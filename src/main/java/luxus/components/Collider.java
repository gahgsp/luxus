package luxus.components;

import luxus.Component;
import luxus.Transform;
import luxus.Window;

public class Collider extends Component {

    /**
     * The transform of this component.
     * It will be always the same as the parent.
     */
    private Transform _transform;

    /**
     * The movement controller of the game object.
     * Both player and NPCs will have a movement controller that is responsible for their movement behavior.
     */
    private MovementController _movementController;

    @Override
    public void start() {
        this._transform = gameObject.getTransform();
        this._movementController = gameObject.getComponent(MovementController.class);
    }

    @Override
    public void update(float deltaTime) {
        for (int index = 0; index < Window.getCurrentScene().getCollidersInScene().size(); index++) {
            Collider otherCollider = Window.getCurrentScene().getCollidersInScene().get(index);
            if (otherCollider.equals(this)) {
                continue;
            }
            if (isColliding(otherCollider)) {
                if (this._movementController.hasHorizontalMovement()) {
                    updateHorizontalPosition(otherCollider);
                } else if (this._movementController.hasVerticalMovement()) {
                    updateVerticalPosition(otherCollider);
                }
            }
        }
    }

    /**
     * Adjust the current horizontal position of the game object that collided horizontally with another object in the scene.
     * @param otherCollider the collider from the other object.
     */
    private void updateHorizontalPosition(Collider otherCollider) {
        if (this._transform.getPosition().x < otherCollider.gameObject.getTransform().getPosition().x) {
            gameObject.getTransform().getPosition().x = otherCollider.gameObject.getTransform().getPosition().x - this._transform.getScale().x;
        } else {
            gameObject.getTransform().getPosition().x = otherCollider.gameObject.getTransform().getPosition().x + otherCollider.gameObject.getTransform().getScale().x;
        }
    }

    /**
     * Adjust the current vertical position of the game object that collided vertically with another object in the scene.
     * @param otherCollider the collider from the other object.
     */
    private void updateVerticalPosition(Collider otherCollider) {
        if (this._transform.getPosition().y < otherCollider.gameObject.getTransform().getPosition().y) {
            gameObject.getTransform().getPosition().y = otherCollider.gameObject.getTransform().getPosition().y - this._transform.getScale().y;
        } else {
            gameObject.getTransform().getPosition().y = otherCollider.gameObject.getTransform().getPosition().y + otherCollider.gameObject.getTransform().getScale().y;
        }
    }

    /**
     * Checks if the current game object is colliding with another game object that also has a collider component.
     * @param otherCollider the collider from the other game in the scene to check if both objects are colliding.
     * @return if the current and the other object are currently colliding with each other.
     */
    private boolean isColliding(Collider otherCollider) {
        float x1 = this._transform.getPosition().x;
        float y1 = this._transform.getPosition().y;
        float w1 = this._transform.getScale().x;
        float h1 = this._transform.getScale().y;

        float x2 = otherCollider.gameObject.getTransform().getPosition().x;
        float y2 = otherCollider.gameObject.getTransform().getPosition().y;
        float w2 = otherCollider.gameObject.getTransform().getScale().x;
        float h2 = otherCollider.gameObject.getTransform().getScale().y;

        return x1 + w1 > x2 && x2 + w2 > x1 && y1 + h1 > y2 && y2 + h2 > y1;
    }
}
