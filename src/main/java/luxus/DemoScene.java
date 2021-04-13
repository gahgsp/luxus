package luxus;

import luxus.camera.Camera;
import luxus.components.Animation;
import luxus.components.CharacterController;
import luxus.components.SpriteRenderer;
import luxus.components.Spritesheet;
import luxus.utils.AssetPool;
import org.joml.Vector2f;

import java.util.HashMap;

public class DemoScene extends Scene {

    public DemoScene() {}

    @Override
    public void init() {
        this._camera = new Camera(new Vector2f(0, 0));

        Map map = new Map("", 16, 16, 32);
        map.loadMap();

        AssetPool.addSpritesheet("idle", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 1, 0));
        Spritesheet idleSprites = AssetPool.getSpritesheet("idle");

        AssetPool.addSpritesheet("top", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 10, 0));
        Spritesheet topSprites = AssetPool.getSpritesheet("top");

        AssetPool.addSpritesheet("right", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 10, 0, 928));
        Spritesheet rightSprites = AssetPool.getSpritesheet("right");

        AssetPool.addSpritesheet("bottom", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 10, 0, 64));
        Spritesheet bottomSprites = AssetPool.getSpritesheet("bottom");

        AssetPool.addSpritesheet("left", new Spritesheet(AssetPool.getTexture("assets/images/link.png"), 32, 32, 10, 0, 96));
        Spritesheet leftSprites = AssetPool.getSpritesheet("left");

        HashMap<String, Animation> playerAnimations = new HashMap<>();
        playerAnimations.put("Idle", new Animation(idleSprites, 999f, 1,false));
        playerAnimations.put("LeftRunning", new Animation(leftSprites, 0.05f, 10,true));
        playerAnimations.put("BottomRunning", new Animation(bottomSprites, 0.05f, 10,true));
        playerAnimations.put("TopRunning", new Animation(topSprites, 0.05f, 10,true));
        playerAnimations.put("RightRunning", new Animation(rightSprites, 0.05f, 10,true));

        GameObject object1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(64, 64)));
        object1.addComponent(new SpriteRenderer(playerAnimations));
        object1.addComponent(new CharacterController());
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
