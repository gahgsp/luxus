package luxus.graphics;

import luxus.Window;
import luxus.components.Collider;
import luxus.utils.AssetPool;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class DebugRenderBatch {

    private Shader _shader;
    private Collider[] _colliders;
    private int _maxBatchSize;
    private int _numberOfColliders;
    private boolean _hasRoom;

    public DebugRenderBatch(int maxBatchSize) {
        this._maxBatchSize = maxBatchSize;

        this._shader = AssetPool.getShader("assets/shaders/default.glsl");

        this._colliders = new Collider[this._maxBatchSize];

        this._numberOfColliders = 0;
        this._hasRoom = true;
    }

    public void addDebugSprite(Collider collider) {
        int index = this._numberOfColliders;
        this._colliders[index] = collider;
        this._numberOfColliders++;

        if (this._numberOfColliders >= this._maxBatchSize) {
            this._hasRoom = false;
        }
    }

    public void render() {
        this._shader.use();
        this._shader.uploadMat4f("uProjection", Window.getCurrentScene().getCamera().getProjectionMatrix());
        this._shader.uploadMat4f("uView", Window.getCurrentScene().getCamera().getViewMatrix());

        glColor3f(0f, 0f, 1f);
        glLineWidth(1f);

        for (int index = 0; index < this._numberOfColliders; index++) {
            var currentObjectTransform = this._colliders[index].gameObject.getTransform();
            glBegin(GL_LINE_LOOP);
            glVertex2f(currentObjectTransform.getPosition().x, currentObjectTransform.getPosition().y);
            glVertex2f(currentObjectTransform.getPosition().x + currentObjectTransform.getScale().x, currentObjectTransform.getPosition().y);
            glVertex2f(currentObjectTransform.getPosition().x + currentObjectTransform.getScale().x, currentObjectTransform.getPosition().y + currentObjectTransform.getScale().y);
            glVertex2f(currentObjectTransform.getPosition().x, currentObjectTransform.getPosition().y + currentObjectTransform.getScale().y);
            glEnd();
        }

        this._shader.detach();
    }

    public boolean hasRoom() {
        return this._hasRoom;
    }
}
