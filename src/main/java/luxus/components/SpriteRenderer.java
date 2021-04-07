package luxus.components;

import luxus.Component;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f _color;

    public SpriteRenderer(Vector4f color) {
        this._color = color;
    }

    @Override
    public void start() {}

    @Override
    public void update(float deltaTime) {}

    public Vector4f getColor() {
        return this._color;
    }
}
