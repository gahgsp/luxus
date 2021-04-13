package luxus;

import luxus.camera.Camera;
import luxus.graphics.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer;

    protected Camera _camera;
    protected List<GameObject> gameObjects;

    private boolean _isRunning;


    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer();
    }

    public void init() {}

    public void start() {
        for (GameObject gameObject : this.gameObjects) {
            gameObject.start();
            this.renderer.add(gameObject);
        }
        _isRunning = true;
    }

    public abstract void update(float deltaTime);

    public void addGameObjectToScene(GameObject gameObject) {
        if (!this._isRunning) {
            this.gameObjects.add(gameObject);
        } else {
            this.gameObjects.add(gameObject);
            gameObject.start();
            this.renderer.add(gameObject);
        }
    }

    public Camera getCamera() {
        return this._camera;
    }

}
