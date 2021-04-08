package luxus.components;

import luxus.Component;
import luxus.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f _color;
    private Texture _texture;

    public SpriteRenderer(Texture texture) {
        this._texture = texture;
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
        return this._texture;
    }

    public Vector2f[] getTextureCoordinates() {
        return new Vector2f[] {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
    }
}
