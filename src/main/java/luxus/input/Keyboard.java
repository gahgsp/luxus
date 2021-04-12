package luxus.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {

    private static Keyboard singletonInstance;

    private boolean[] _keysPressed = new boolean[350];

    private Keyboard() {}

    public static Keyboard getInstance() {
        if (Keyboard.singletonInstance == null) {
            Keyboard.singletonInstance = new Keyboard();
        }
        return Keyboard.singletonInstance;
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
