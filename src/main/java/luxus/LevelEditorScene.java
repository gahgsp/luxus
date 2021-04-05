package luxus;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String _vertexShaderSource = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main() {\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String _fragmentShaderSource = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main() {\n" +
            "    color = fColor;\n" +
            "}";
    private int _vertexId, _fragmentId, shaderProgram;
    private float[] _verticesArray = {
            // Position             // Color
            0.5f, -0.5f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f, // Bottom Right
            -0.5f, 0.5f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f, // Top Left
            0.5f, 0.5f, 0.0f,       0.0f, 0.0f, 1.0f, 1.0f, // Top Right
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f // Bottom Left
    };
    // DISCLAIMER: Must be in "counter-clockwise" order!
    private int[] _elementsArray = {
            2, 1, 0, // Top Right Triangle
            0, 1, 3 // Bottom Left Triangle
    };
    private int _vaoId, _vboId, _eboId;

    public LevelEditorScene() {}

    @Override
    public void init() {
        /**
         * Compile and link the shaders.
         */
        // Load and compile the Vertex shader.
        _vertexId = glCreateShader(GL_VERTEX_SHADER);
        // Send the shader source to the GPU.
        glShaderSource(_vertexId, _vertexShaderSource);
        // Compiling the Vertex shader.
        glCompileShader(_vertexId);

        // Check for errors during compilation.
        int success = glGetShaderi(_vertexId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(_vertexId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: The default shader 'default.glsl' has thrown an error!\n\tVertex Shader compilation has failed!");
            System.err.println(glGetShaderInfoLog(_vertexId, length));
            assert false : "";
        }

        // Load and compile the Fragment shader.
        _fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        // Send the shader source to the GPU.
        glShaderSource(_fragmentId, _fragmentShaderSource);
        // Compiling the Fragment shader.
        glCompileShader(_fragmentId);

        // Check for errors during compilation.
        success = glGetShaderi(_fragmentId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(_fragmentId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: The default shader 'default.glsl' has thrown an error!\n\tFragment Shader compilation has failed!");
            System.err.println(glGetShaderInfoLog(_fragmentId, length));
            assert false : "";
        }

        // Link the shaders and check for errors.
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, _vertexId);
        glAttachShader(shaderProgram, _fragmentId);
        glLinkProgram(shaderProgram);

        // Check for linking errors.
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int length = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: The default shader 'default.glsl' has thrown an error!\n\tShader Program Linking has failed!");
            System.err.println(glGetProgramInfoLog(shaderProgram, length));
            assert false : "";
        }

        /**
         * Generate the VAO, VBO and EBO buffer objects.
         * After that, send all to the GPU.
         */
        _vaoId = glGenVertexArrays();
        glBindVertexArray(_vaoId);

        // Creating a float buffer of vertices.
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(_verticesArray.length);
        vertexBuffer.put(_verticesArray).flip();

        // Creating the VBO.
        _vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, _vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Creating the indexes and upload buffers.
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(_elementsArray.length);
        elementBuffer.put(_elementsArray).flip();

        _eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Adding the vertices attributes pointers.
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float deltaTime) {
        // Binding our shader program.
        glUseProgram(shaderProgram);

        // Binding the VAO.
        glBindVertexArray(_vaoId);

        // Enabling the vertices attributes pointers.
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Drawing the elements on the screen.
        glDrawElements(GL_TRIANGLES, _elementsArray.length, GL_UNSIGNED_INT, 0);

        // Finally, we need to unbind everything.
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glUseProgram(0);
    }
}
