package luxus;

import luxus.listener.KeyboardListener;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

public class LevelEditorScene extends Scene {

    private boolean _isChangingScene;
    private float _timeToChangeScene = 2f;

    public LevelEditorScene() {
        System.out.println("Started Level Scene Editor!");
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("Running 'Level Scene Editor' at " + (1f / deltaTime) + " FPS!");

        if (!_isChangingScene && KeyboardListener.isKeyPressed(GLFW_KEY_SPACE)) {
            _isChangingScene = true;
        }

        if (_isChangingScene && _timeToChangeScene > 0) {
            _timeToChangeScene -= deltaTime;
            Window.getInstance().r -= deltaTime * 5f;
            Window.getInstance().g -= deltaTime * 5f;
            Window.getInstance().b -= deltaTime * 5f;
        } else if (_isChangingScene) {
            Window.changeScene(1);
        }
    }
}
