package luxus;

import org.joml.Vector2f;

public class Transform {

    private Vector2f _position;
    private Vector2f _scale;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    private void init(Vector2f position, Vector2f scale) {
        this._position = position;
        this._scale = scale;
    }

    public Vector2f getPosition() {
        return this._position;
    }

    public Vector2f getScale() {
        return this._scale;
    }
}
