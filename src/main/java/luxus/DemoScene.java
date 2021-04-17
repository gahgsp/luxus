package luxus;

import luxus.camera.Camera;
import luxus.components.CharacterController;
import luxus.components.Collider;
import luxus.components.SpriteRenderer;
import luxus.graphics.Animation;
import luxus.graphics.Sprite;
import luxus.graphics.Spritesheet;
import luxus.input.Keyboard;
import luxus.utils.AssetPool;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class DemoScene extends Scene {

    private GameObject _mainCharacter;

    public DemoScene() {}

    @Override
    public void init() {
        this._camera = new Camera();

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

        _mainCharacter = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(64, 64)));
        _mainCharacter.addComponent(new SpriteRenderer(playerAnimations));
        _mainCharacter.addComponent(new CharacterController());
        _mainCharacter.addComponent(new Collider());
        this.addGameObjectToScene(_mainCharacter);

        GameObject object2 = new GameObject("Object 2", new Transform(new Vector2f(300, 100), new Vector2f(32, 32)));
        object2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/fire_arrow.png"))));
        object2.addComponent(new Collider());
        this.addGameObjectToScene(object2);
    }

    @Override
    public void update(float deltaTime) {
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_C)) {
            this.setIsDebugMode(true);
        }

        for (int index = 0; index < this.gameObjects.size(); index++) {
            this.gameObjects.get(index).update(deltaTime);
        }

        this._camera.smoothFollow(_mainCharacter.getTransform());
        this.renderer.render();
    }
}
