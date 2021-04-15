package luxus;

import luxus.camera.Camera;
import luxus.components.Collider;
import luxus.graphics.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera _camera;
    protected List<GameObject> gameObjects;
    protected Renderer renderer;

    private boolean _isRunning;
    private List<Collider> _collidersInScene;

    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer();
        this._collidersInScene = new ArrayList<>();
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

        // Grouping all the objects in the scene that have a Collider Component
        // to be faster and easier to check for collisions.
        if (gameObject.getComponent(Collider.class) != null) {
            this._collidersInScene.add(gameObject.getComponent(Collider.class));
        }
    }

    public Camera getCamera() {
        return this._camera;
    }

    public List<Collider> getCollidersInScene() {
        return this._collidersInScene;
    }

}
