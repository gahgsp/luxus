package luxus;

import luxus.camera.Camera;
import luxus.components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {}

    @Override
    public void init() {
        this._camera = new Camera(new Vector2f(-250, 0));

        int xOffset = 10;
        int yOffset = 10;

        float screenWidth = (float) (600 - xOffset * 2);
        float screenHeight = (float) (300 - yOffset * 2);
        float xSize = screenWidth / 100f;
        float ySize = screenHeight / 100f;

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPosition = xOffset + (x * xSize);
                float yPosition = yOffset + (y * ySize);

                GameObject gameObject = new GameObject("Object" + x + "" + "y", new Transform(new Vector2f(xPosition, yPosition), new Vector2f(xSize, ySize)));
                gameObject.addComponent(new SpriteRenderer(new Vector4f(xPosition / screenWidth, yPosition / screenHeight, 1 ,1)));
                this.addGameObjectToScene(gameObject);
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        this.gameObjects.forEach(gameObject -> gameObject.update(deltaTime));
        this.renderer.render();
    }
}
