package luxus;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private String _name;
    private List<Component> _components;

    public GameObject(String name) {
        this._name = name;
        this._components = new ArrayList<>();
    }

    public void addComponent(Component component) {
        this._components.add(component);
        component.gameObject = this;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component component : this._components) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                try {
                    return componentClass.cast(component);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "ERROR: Could not cast the component!";
                }
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int index = 0; index < this._components.size(); index++) {
            Component component = this._components.get(index);
            if (componentClass.isAssignableFrom(component.getClass())) {
                this._components.remove(index);
                return;
            }
        }
    }

    public void start() {
        for (Component component : this._components) {
            component.start();
        }
    }

    public void update(float deltaTime) {
        for (Component component : this._components) {
            component.update(deltaTime);
        }
    }
}
