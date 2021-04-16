package luxus.components;

import luxus.input.Keyboard;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class CharacterController extends MovementController {

    private Vector2f _position = new Vector2f(0f, 0f);
    private Vector2f _velocity = new Vector2f(100f, 100f);
    private SpriteRenderer _spriteRenderer;

    private boolean _hasHorizontalMovement;
    private boolean _hasVerticalMovement;

    @Override
    public void start() {
        this._position = gameObject.getTransform().getPosition();
        this._spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
    }

    @Override
    public void update(float deltaTime) {
        // No diagonal movement around here...
        if (!this._hasVerticalMovement) {
            moveHorizontally(deltaTime);
        }
        if (!this._hasHorizontalMovement) {
            moveVertically(deltaTime);
        }

        // No movement? So we are idle...
        if (!this._hasHorizontalMovement && !this._hasVerticalMovement) {
            gameObject.getComponent(SpriteRenderer.class).playAnimation("Idle");
        }
    }

    private void moveHorizontally(float deltaTime) {
        int dx = 0;
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_D)) {
            dx = 1;
            this._spriteRenderer.playAnimation("RightRunning");
        } else if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_A)) {
            dx = -1;
            this._spriteRenderer.playAnimation("LeftRunning");
        }

        this._position.x += this._velocity.x * deltaTime * dx;
        this._hasHorizontalMovement = dx != 0;
    }

    private void moveVertically(float deltaTime) {
        int dy = 0;
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_W)) {
            dy = 1;
            this._spriteRenderer.playAnimation("TopRunning");
        } else if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_S)) {
            dy = -1;
            this._spriteRenderer.playAnimation("BottomRunning");
        }

        this._position.y += this._velocity.y * deltaTime * dy;
        this._hasVerticalMovement = dy != 0;
    }

    @Override
    public boolean hasHorizontalMovement() {
        return this._hasHorizontalMovement;
    }

    @Override
    public boolean hasVerticalMovement() {
        return this._hasVerticalMovement;
    }
}
