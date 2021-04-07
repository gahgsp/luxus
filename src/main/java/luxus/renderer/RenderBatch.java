package luxus.renderer;

import luxus.Window;
import luxus.components.SpriteRenderer;
import luxus.utils.AssetPool;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch {
    // Vertex Structure
    // -----------------------------------------------
    // Position             Color
    // float, float         float, float, float, float
    private final int POSITION_SIZE = 2;
    private final int COLOR_SIZE = 4;

    private final int POSITION_OFFSET = 0;
    private final int COLOR_OFFSET = POSITION_OFFSET + POSITION_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_IN_BYTES = VERTEX_SIZE * Float.BYTES;

    private SpriteRenderer[] _sprites;
    private int _numberOfSprites, _vaoId, _vboId, _maxBatchSize;
    private boolean _hasRoom;
    private float[] _vertices;
    private Shader _shader;

    public RenderBatch(int maxBatchSize) {
        this._maxBatchSize = maxBatchSize;

        this._shader = AssetPool.getShader("assets/shaders/default.glsl");

        this._sprites = new SpriteRenderer[this._maxBatchSize];

        // Each sprite will be a "Quad", so there are 4 vertices in each sprite.
        this._vertices = new float[this._maxBatchSize * 4 * VERTEX_SIZE];

        this._numberOfSprites = 0;
        this._hasRoom = true;
    }

    public void start() {
        // Generates and binds a Vertex Array Object.
        this._vaoId = glGenVertexArrays();
        glBindVertexArray(_vaoId);

        // Allocates the space for the vertices.
        this._vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, _vboId);
        glBufferData(GL_ARRAY_BUFFER, _vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Creates and uploads the buffer indices.
        int eboId = glGenBuffers();
        int[] indices = generateIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Enables the buffer attributes pointers.
        glVertexAttribPointer(0, POSITION_SIZE, GL_FLOAT, false, VERTEX_SIZE_IN_BYTES, POSITION_OFFSET);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_IN_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void addSprite(SpriteRenderer spriteRenderer) {
        // Get the index and add the renderer object.
        int index = this._numberOfSprites;
        this._sprites[index] = spriteRenderer;
        this._numberOfSprites++;

        // Add the properties to the local vertices array.
        loadVertexProperties(index);

        if (this._numberOfSprites >= this._maxBatchSize) {
            this._hasRoom = false;
        }
    }

    public void render() {
        // For now, we will rebuffer all data every frame.
        // TODO: This can be optmized!
        glBindBuffer(GL_ARRAY_BUFFER, this._vboId);
        glBufferSubData(GL_ARRAY_BUFFER, 0, this._vertices);

        // Making sure we use our shader!
        this._shader.use();
        this._shader.uploadMat4f("uProjection", Window.getCurrentScene().getCamera().getProjectionMatrix());
        this._shader.uploadMat4f("uView", Window.getCurrentScene().getCamera().getViewMatrix());

        glBindVertexArray(this._vaoId);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, this._numberOfSprites * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        this._shader.detach();
    }

    private void loadVertexProperties(int index) {
        SpriteRenderer spriteRenderer = this._sprites[index];

        // Finds the offset within the array (4 vertices per sprite).
        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = spriteRenderer.getColor();

        // Adds the vertices with the appropriate properties.
        // V3 (0, 1)    V2 (1, 1)
        // V4 (0, 0)    V1 (1, 0)
        float xAdd = 1f;
        float yAdd = 1f;
        for (int verticesIndex = 0; verticesIndex < 4; verticesIndex++) {
            if (verticesIndex == 1) {
                yAdd = 0f;
            } else if (verticesIndex == 2) {
                xAdd = 0f;
            } else if (verticesIndex == 3) {
                yAdd = 1f;
            }

            // Loads the position.
            this._vertices[offset] = spriteRenderer.gameObject.transform.getPosition().x + (xAdd * spriteRenderer.gameObject.transform.getScale().x);
            this._vertices[offset + 1] = spriteRenderer.gameObject.transform.getPosition().y + (yAdd * spriteRenderer.gameObject.transform.getScale().y);

            // Loads the color.
            this._vertices[offset + 2] = color.x;
            this._vertices[offset + 3] = color.y;
            this._vertices[offset + 4] = color.z;
            this._vertices[offset + 5] = color.w;

            offset += VERTEX_SIZE;
        }
    }

    private int[] generateIndices() {
        // We have 6 indices per "Quad" (3 indices per triangle).
        int[] elements = new int[6 * this._maxBatchSize];
        for (int index = 0; index < this._maxBatchSize; index++) {
            loadElementIndices(elements, index);
        }
        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;

        // First triangle from the "Quad".
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex + 1] = offset + 2;
        elements[offsetArrayIndex + 2] = offset;

        // Second triangle from the "Quad".
        elements[offsetArrayIndex + 3] = offset;
        elements[offsetArrayIndex + 4] = offset + 2;
        elements[offsetArrayIndex + 5] = offset + 1;
    }

    public boolean hasRoom() {
        return this._hasRoom;
    }
}
