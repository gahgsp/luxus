package luxus.graphics;

import luxus.GameObject;
import luxus.components.Collider;
import luxus.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final int MAX_BATCH_SIZE = 1000;

    private List<RenderBatch> _batches;
    private List<DebugRenderBatch> _debugBatches;

    public Renderer() {
        this._batches = new ArrayList<>();
        this._debugBatches = new ArrayList<>();
    }

    public void add(GameObject gameObject) {
        SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
        if (spriteRenderer != null) {
            addDefault(spriteRenderer);
        }

        Collider collider = gameObject.getComponent(Collider.class);
        if (collider != null) {
            addDebug(collider);
        }
    }

    private void addDefault(SpriteRenderer spriteRenderer) {
        boolean added = false;
        for (RenderBatch batch : this._batches) {
            if (batch.hasRoom()) {
                Texture texture = spriteRenderer.getTexture();
                if (texture == null || (batch.hasTexture(texture) || batch.hasTextureRoom())) {
                    batch.addSprite(spriteRenderer);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            this._batches.add(newBatch);
            newBatch.addSprite(spriteRenderer);
        }
    }

    private void addDebug(Collider collider) {
        boolean added = false;
        for (DebugRenderBatch batch : this._debugBatches) {
            if (batch.hasRoom()) {
                batch.addDebugSprite(collider);
                added = true;
                break;
            }
        }

        if (!added) {
            DebugRenderBatch newBatch = new DebugRenderBatch(MAX_BATCH_SIZE);
            this._debugBatches.add(newBatch);
            newBatch.addDebugSprite(collider);
        }
    }

    public void render() {
        // Rendering the default batch.
        for (RenderBatch batch : this._batches) {
            batch.render();
        }
        // Rendering the debug batch (only for internal purposes).
        for (DebugRenderBatch debugRenderBatch : this._debugBatches) {
            debugRenderBatch.render();
        }
    }
}
