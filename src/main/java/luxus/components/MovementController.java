package luxus.components;

import luxus.Component;

/**
 * A superclass to define the movement behavior of animated objects in the game scene.
 */
public abstract class MovementController extends Component {

    @Override
    public void update(float deltaTime) {
        // Do nothing.
        // Other classes will override.
    }

    /**
     * Return if the parent has horizontal movement.
     * @return if the parent has horizontal movement.
     */
    public abstract boolean hasHorizontalMovement();

    /**
     * Return if the parent has vertical movement.
     * @return if the parent has vertical movement.
     */
    public abstract boolean hasVerticalMovement();

}
