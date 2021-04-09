package luxus;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private String _name;
    private List<Component> _components;
    private Transform _transform;

    public GameObject(String name) {
        this._name = name;
        this._components = new ArrayList<>();
        this._transform = new Transform();
    }

    public GameObject(String name, Transform transform) {
        this._name = name;
        this._components = new ArrayList<>();
        this._transform = transform;
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
        for (int index = 0; index < this._components.size(); index++) {
            this._components.get(index).start();
        }
    }

    public void update(float deltaTime) {
        for (int index = 0; index < this._components.size(); index++) {
            this._components.get(index).update(deltaTime);
        }
    }

    public Transform getTransform() {
        return this._transform;
    }
}
