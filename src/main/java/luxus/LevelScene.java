package luxus;

public class LevelScene extends Scene {

    public LevelScene() {
        System.out.println("Started Level Scene!");
        Window.getInstance().r = 1f;
        Window.getInstance().g = 1f;
        Window.getInstance().b = 1f;
    }

    @Override
    public void update(float deltaTime) {

    }
}
