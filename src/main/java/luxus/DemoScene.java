package luxus;

import luxus.camera.Camera;
import luxus.components.Animation;
import luxus.components.Spritesheet;
import luxus.utils.AssetPool;
import org.joml.Vector2f;

public class DemoScene extends Scene {

    public DemoScene() {}

    @Override
    public void init() {
        this._camera = new Camera(new Vector2f(0, 0));

        Map map = new Map("", 16, 16, 32);
        map.loadMap();

        AssetPool.addSpritesheet("assets/images/link.png", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 10, 0));
        Spritesheet spritesheet = AssetPool.getSpritesheet("assets/images/link.png");

        GameObject object1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(64, 64)));
        object1.addComponent(new Animation(spritesheet, 0.1f, 10,true));
        this.addGameObjectToScene(object1);
    }

    @Override
    public void update(float deltaTime) {
        for(int index = 0; index < this.gameObjects.size(); index++) {
            this.gameObjects.get(index).update(deltaTime);
        }
        this.renderer.render();
    }
}
