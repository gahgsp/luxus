package luxus.graphics;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1iv;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Shader {

    private int _shaderProgramId;
    private String _vertexSource;
    private String _fragmentSource;
    private String _filePath;
    private boolean beingUsed;

    public Shader(String filePath) {
        this._filePath = filePath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // Look for the first pattern from the Shader file that is after '#type' decorator.
            int index = source.indexOf("#type") + 6;
            int endOfLine = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, endOfLine).trim();

            // Look for the second pattern from the Shader file that is after '#type' decorator.
            index = source.indexOf("#type", endOfLine) + 6;
            endOfLine = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, endOfLine).trim();

            if (firstPattern.equals("vertex")) {
                _vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                _fragmentSource = splitString[1];
            } else {
                throw new IOException("ERROR: Unexpected token '" + firstPattern + "' in file '" + filePath + "'!");
            }

            if (secondPattern.equals("vertex")) {
                _vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                _fragmentSource = splitString[2];
            } else {
                throw new IOException("ERROR: Unexpected token '" + secondPattern + "' in file '" + filePath + "'!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            assert false : "ERROR: Could not open the Shader file: '" + filePath + "'";
        }
    }

    /**
     * Compiles and link the shaders.
     */
    public void compile() {
        int vertexId, fragmentId;
        // Load and compile the Vertex shader.
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        // Send the shader source to the GPU.
        glShaderSource(vertexId, _vertexSource);
        // Compiling the Vertex shader.
        glCompileShader(vertexId);

        // Check for errors during compilation.
        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: The default shader '"+ _filePath + "' has thrown an error!\n\tVertex Shader compilation has failed!");
            System.err.println(glGetShaderInfoLog(vertexId, length));
            assert false : "";
        }

        // Load and compile the Fragment shader.
        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        // Send the shader source to the GPU.
        glShaderSource(fragmentId, _fragmentSource);
        // Compiling the Fragment shader.
        glCompileShader(fragmentId);

        // Check for errors during compilation.
        success = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: The default shader '" + _filePath + "' has thrown an error!\n\tFragment Shader compilation has failed!");
            System.err.println(glGetShaderInfoLog(fragmentId, length));
            assert false : "";
        }

        // Link the shaders and check for errors.
        _shaderProgramId = glCreateProgram();
        glAttachShader(_shaderProgramId, vertexId);
        glAttachShader(_shaderProgramId, fragmentId);
        glLinkProgram(_shaderProgramId);

        // Check for linking errors.
        success = glGetProgrami(_shaderProgramId, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int length = glGetProgrami(_shaderProgramId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: The default shader '" + _filePath + "' has thrown an error!\n\tShader Program Linking has failed!");
            System.err.println(glGetProgramInfoLog(_shaderProgramId, length));
            assert false : "";
        }
    }

    public void use() {
        if (!beingUsed) {
            // Binding our shader program.
            glUseProgram(_shaderProgramId);
            beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        beingUsed = false;
    }

    public void uploadMat4f(String variableName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(_shaderProgramId, variableName);
        // Making sure that we are using the shader!
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String variableName, Vector4f vec4f) {
        int varLocation = glGetUniformLocation(_shaderProgramId, variableName);
        // Making sure that we are using the shader!
        use();
        glUniform4f(varLocation, vec4f.x, vec4f.y, vec4f.z, vec4f.w);
    }

    public void uploadFloat(String variableName, float value) {
        int varLocation = glGetUniformLocation(_shaderProgramId, variableName);
        // Making sure that we are using the shader!
        use();
        glUniform1f(varLocation, value);
    }

    public void uploadTexture(String variableName, int textureSlot) {
        int varLocation = glGetUniformLocation(_shaderProgramId, variableName);
        // Making sure that we are using the shader!
        use();
        glUniform1i(varLocation, textureSlot);
    }

    public void uploadIntArray(String variableName, int[] array) {
        int varLocation = glGetUniformLocation(_shaderProgramId, variableName);
        // Making sure that we are using the shader!
        use();
        glUniform1iv(varLocation, array);
    }
}
