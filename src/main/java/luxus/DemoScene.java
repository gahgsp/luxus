package luxus;

import luxus.camera.Camera;
import luxus.components.SpriteRenderer;
import luxus.components.Spritesheet;
import luxus.utils.AssetPool;
import org.joml.Vector2f;

public class DemoScene extends Scene {

    public DemoScene() {}

    @Override
    public void init() {
        this._camera = new Camera(new Vector2f(-250, 0));

        AssetPool.addSpritesheet("assets/images/link.png", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 22, 0));
        Spritesheet spritesheet = AssetPool.getSpritesheet("assets/images/link.png");

        GameObject object1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(64, 64)));
        object1.addComponent(new SpriteRenderer(spritesheet.getSprite(0)));
        this.addGameObjectToScene(object1);

        GameObject object2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(64, 64)));
        object2.addComponent(new SpriteRenderer(spritesheet.getSprite(11)));
        this.addGameObjectToScene(object2);
    }

    @Override
    public void update(float deltaTime) {
        this.gameObjects.forEach(gameObject -> gameObject.update(deltaTime));
        this.renderer.render();
    }
}
