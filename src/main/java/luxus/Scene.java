package luxus;

import luxus.camera.Camera;

public abstract class Scene {

    protected Camera _camera;

    public Scene() {}

    public void init() {}

    public abstract void update(float deltaTime);

}
