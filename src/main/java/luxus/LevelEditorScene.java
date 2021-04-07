package luxus;

import luxus.camera.Camera;
import luxus.renderer.Shader;
import luxus.renderer.Texture;
import luxus.utils.Time;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private float[] _verticesArray = {
            // Position               // Color                   // UV Coordinate
            100f, -0.5f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f,    1,0, // Bottom Right
            -0.5f, 100f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f,    0,1, // Top Left
            100f, 100f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f,    1,1, // Top Right
            -0.5f, -0.5f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f,    0,0  // Bottom Left
    };
    // DISCLAIMER: Must be in "counter-clockwise" order!
    private int[] _elementsArray = {
            2, 1, 0, // Top Right Triangle
            0, 1, 3 // Bottom Left Triangle
    };
    private int _vaoId, _vboId, _eboId;
    private Shader _defaultShader;

    private Texture _testTexture;

    public LevelEditorScene() {}

    @Override
    public void init() {
        _camera = new Camera(new Vector2f(-200, -300));
        // Accessing and loading the Shader file.
        _defaultShader = new Shader("assets/shaders/default.glsl");
        _defaultShader.compile();

        _testTexture = new Texture("assets/images/texture.png");

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
        int uvSize = 2;
        int vertexSizeBytes = (positionSize + colorSize + uvSize) * Float.BYTES;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float deltaTime) {
        _defaultShader.use();

        // Uploading the texture to the shader.
        _defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        _testTexture.bind();

        _defaultShader.uploadMat4f("uProjection", _camera.getProjectionMatrix());
        _defaultShader.uploadMat4f("uView", _camera.getViewMatrix());
        _defaultShader.uploadFloat("uTime", Time.getTime());

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
        _defaultShader.detach();
    }
}
