package luxus.components;

import luxus.Component;
import luxus.Transform;
import luxus.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f _color;
    private Sprite _sprite;

    private Transform _lastTransform;
    private boolean _isSpriteDirty;

    public SpriteRenderer(Sprite sprite) {
        this._sprite = sprite;
        this._color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {
        this._isSpriteDirty = true;
        this._lastTransform = gameObject.getTransform().copy();
    }

    @Override
    public void update(float deltaTime) {
        if (!this._lastTransform.equals(this.gameObject.getTransform())) {
            this.gameObject.getTransform().copy(this._lastTransform);
            this._isSpriteDirty = true;
        }
    }

    public Vector4f getColor() {
        return this._color;
    }

    public void setSprite(Sprite newSprite) {
        this._sprite = newSprite;
        this._isSpriteDirty = true;
    }

    public Texture getTexture() {
        return this._sprite.getTexture();
    }

    public Vector2f[] getTextureCoordinates() {
        return this._sprite.getTextureCoordinates();
    }

    public boolean isDirty() {
        return this._isSpriteDirty;
    }

    public void cleanSpriteRenderer() {
        this._isSpriteDirty = false;
    }
}
