package luxus;

public abstract class Component {

    public GameObject gameObject;

    public void start() {};

    public abstract void update(float deltaTime);
}
