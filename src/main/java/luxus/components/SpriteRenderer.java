package luxus.components;

import luxus.Component;

public class SpriteRenderer extends Component {

    private boolean _firstTime;

    @Override
    public void start() {
        System.out.println("I am Starting!");
    }

    @Override
    public void update(float deltaTime) {
        if (!this._firstTime) {
            System.out.println("I am Updating!");
            this._firstTime = true;
        }

    }
}
