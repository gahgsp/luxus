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

    public void setPosition(Vector2f position) {
        this._position = position;
    }

    public void setScale(Vector2f scale) {
        this._scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2f(this.getPosition()), new Vector2f(this.getScale()));
    }

    public void copy(Transform transform) {
        transform.setPosition(this.getPosition());
        transform.setScale(this.getPosition());
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        }
        if (!(otherObject instanceof Transform)) {
            return false;
        }
        Transform otherTransform = (Transform) otherObject;
        return otherTransform.getPosition().equals(this.getPosition()) && otherTransform.getScale().equals(this.getScale());
    }
}
