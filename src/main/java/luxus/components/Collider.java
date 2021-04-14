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

    @Override
    public void start() {
        this._transform = gameObject.getTransform();
    }

    @Override
    public void update(float deltaTime) {
        for (int index = 0; index < Window.getCurrentScene().getCollidersInScene().size(); index++) {
            if (Window.getCurrentScene().getCollidersInScene().get(index).equals(this)) {
                continue;
            }
            if (isColliding(Window.getCurrentScene().getCollidersInScene().get(index))) {
                System.out.println("It is colliding!");
            }
        }
    }

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
