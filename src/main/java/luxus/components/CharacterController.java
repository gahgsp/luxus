package luxus.components;

import luxus.Component;
import luxus.input.Keyboard;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class CharacterController extends Component {

    private Vector2f _position = new Vector2f(0f, 0f);
    private Vector2f _velocity = new Vector2f(100f, 100f);

    @Override
    public void start() {
        this._position = gameObject.getTransform().getPosition();
    }

    @Override
    public void update(float deltaTime) {
        moveHorizontally(deltaTime);
        moveVertically(deltaTime);
    }

    private void moveHorizontally(float deltaTime) {
        gameObject.getTransform().getPosition().x = this._position.x;
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_D)) {
            gameObject.getTransform().getPosition().x += this._velocity.x * deltaTime;
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_A)) {
            gameObject.getTransform().getPosition().x -= this._velocity.x * deltaTime;
        }
    }

    private void moveVertically(float deltaTime) {
        gameObject.getTransform().getPosition().y = this._position.y;
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_W)) {
            gameObject.getTransform().getPosition().y += this._velocity.y * deltaTime;
        }
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_S)) {
            gameObject.getTransform().getPosition().y -= this._velocity.y * deltaTime;
        }
    }
}
