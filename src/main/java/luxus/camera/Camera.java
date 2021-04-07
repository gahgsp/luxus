package luxus.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f _projectionMatrix, _viewMatrix;
    private Vector2f _position;

    public Camera(Vector2f position) {
        this._position = position;
        this._projectionMatrix = new Matrix4f();
        this._viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        _projectionMatrix.identity();
        _projectionMatrix.ortho(0f, 32f * 40f, 0f, 32f * 21f, 0f, 100f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0f, 0f, -1f);
        Vector3f cameraUp = new Vector3f(0f, 1f, 0f);
        _viewMatrix.identity();
        _viewMatrix = _viewMatrix.lookAt(
                new Vector3f(_position.x, _position.y, 20f), // Where the camera is now.
                cameraFront.add(_position.x, _position.y, 0f), // Where is the center of the camera.
                cameraUp);
        return _viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return _projectionMatrix;
    }

    public Vector2f getPosition() {
        return _position;
    }

    public void setPosition(Vector2f position) {
        this._position = position;
    }
}
