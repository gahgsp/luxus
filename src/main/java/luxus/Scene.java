package luxus;

import luxus.camera.Camera;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera _camera;
    protected List<GameObject> gameObjects;

    private boolean _isRunning;


    public Scene() {
        this.gameObjects = new ArrayList<>();
    }

    public void init() {}

    public void start() {
        for (GameObject gameObject : this.gameObjects) {
            gameObject.start();
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
        }
    }

}
