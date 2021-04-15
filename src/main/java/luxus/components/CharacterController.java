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
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_D)) {
            this._position.x += this._velocity.x * deltaTime;
            this._spriteRenderer.playAnimation("RightRunning");
            this._hasHorizontalMovement = true;
        } else if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_A)) {
            this._position.x -= this._velocity.x * deltaTime;
            this._spriteRenderer.playAnimation("LeftRunning");
            this._hasHorizontalMovement = true;
        } else {
            this._hasHorizontalMovement = false;
        }
    }

    private void moveVertically(float deltaTime) {
        if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_W)) {
            this._position.y += this._velocity.y * deltaTime;
            this._spriteRenderer.playAnimation("TopRunning");
            this._hasVerticalMovement = true;
        } else if (Keyboard.isKeyPressed(GLFW.GLFW_KEY_S)) {
            this._position.y -= this._velocity.y * deltaTime;
            this._spriteRenderer.playAnimation("BottomRunning");
            this._hasVerticalMovement = true;
        } else {
            this._hasVerticalMovement = false;
        }
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
