package luxus.renderer;

import luxus.GameObject;
import luxus.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final int MAX_BATCH_SIZE = 1000;

    private List<RenderBatch> _batches;

    public Renderer() {
        this._batches = new ArrayList<>();
    }

    public void add(GameObject gameObject) {
        SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
        if (spriteRenderer != null) {
            add(spriteRenderer);
        }
    }

    private void add(SpriteRenderer spriteRenderer) {
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

    public void render() {
        for (RenderBatch batch : this._batches) {
            batch.render();
        }
    }
}
