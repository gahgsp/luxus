package luxus.components;

import luxus.Component;
import luxus.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f _color;
    private Sprite _sprite;

    public SpriteRenderer(Sprite sprite) {
        this._sprite = sprite;
        this._color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {}

    @Override
    public void update(float deltaTime) {}

    public Vector4f getColor() {
        return this._color;
    }

    public Texture getTexture() {
        return this._sprite.getTexture();
    }

    public Vector2f[] getTextureCoordinates() {
        return this._sprite.getTextureCoordinates();
    }
}
