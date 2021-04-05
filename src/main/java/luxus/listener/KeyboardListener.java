package luxus.listener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardListener {

    private static KeyboardListener singletonInstance;

    private boolean[] _keysPressed = new boolean[350];

    private KeyboardListener() {}

    public static KeyboardListener getInstance() {
        if (KeyboardListener.singletonInstance == null) {
            KeyboardListener.singletonInstance = new KeyboardListener();
        }
        return KeyboardListener.singletonInstance;
    }

    public static void keyCallback(long window, int key, int scanCode, int action, int mods) {
        if (action == GLFW_PRESS) {
            getInstance()._keysPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            getInstance()._keysPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return getInstance()._keysPressed[keyCode];
    }
}
