package luxus.camera;

import luxus.Transform;
import luxus.Window;
import luxus.utils.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f _projectionMatrix, _viewMatrix;
    private Vector2f _position;

    public Camera() {
        this(new Vector2f(0f, 0f));
    }

    public Camera(Vector2f position) {
        this._position = position;
        this._projectionMatrix = new Matrix4f();
        this._viewMatrix = new Matrix4f();
        adjustProjection();
    }

    private void adjustProjection() {
        this._projectionMatrix.identity();
        this._projectionMatrix.ortho(0f, 32f * 40f, 0f, 32f * 21f, 0f, 100f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0f, 0f, -1f);
        Vector3f cameraUp = new Vector3f(0f, 1f, 0f);
        this._viewMatrix.identity();
        this._viewMatrix = this._viewMatrix.lookAt(
                new Vector3f(this._position.x, this._position.y, 20f), // Where the camera is now.
                cameraFront.add(this._position.x, this._position.y, 0f), // Where is the center of the camera.
                cameraUp);
        return this._viewMatrix;
    }

    /**
     * Follow the target making the object be always in the center of the screen.
     * @param target the target that the camera must follow.
     */
    public void follow(Transform target) {
        Vector2f desired = new Vector2f(target.getPosition().x - (Window.getWidth() / 4f), target.getPosition().y - (Window.getHeight() / 4f));
        this._position = desired;
    }

    /**
     * Follow the target with a smoothing motion making the object be always in the center of the screen.
     * @param target
     */
    public void smoothFollow(Transform target) {
        float smoothFactor = 0.045f;
        Vector2f desired = new Vector2f(target.getPosition().x - (Window.getWidth() / 4f), target.getPosition().y - (Window.getHeight() / 4f));
        Vector2f follow = new Vector2f(MathUtils.lerp(this._position.x, desired.x, smoothFactor), MathUtils.lerp(this._position.y, desired.y, smoothFactor));
        this._position = follow;
    }

    public Matrix4f getProjectionMatrix() {
        return this._projectionMatrix;
    }
}
