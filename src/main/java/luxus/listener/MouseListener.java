package luxus.listener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener singletonInstance = null;

    private double _scrollX, _scrollY;
    private double _xPos, _yPos, _lastX, _lastY;
    private boolean[] _mouseButtonsPressed = new boolean[3];
    private boolean _isDragging;

    private MouseListener() {
        this._scrollX = 0.0;
        this._scrollY = 0.0;
        this._xPos = 0.0;
        this._yPos = 0.0;
        this._lastX = 0.0;
        this._lastY = 0.0;
    }

    public static MouseListener getInstance() {
        if (MouseListener.singletonInstance == null) {
            MouseListener.singletonInstance = new MouseListener();
        }
        return MouseListener.singletonInstance;
    }

    public static void mousePositionCallback(long window, double xPos, double yPos) {
        getInstance()._lastX = getInstance()._xPos;
        getInstance()._lastY = getInstance()._yPos;

        getInstance()._xPos = xPos;
        getInstance()._yPos = yPos;

        getInstance()._isDragging = getInstance()._mouseButtonsPressed[0] || getInstance()._mouseButtonsPressed[1] || getInstance()._mouseButtonsPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < getInstance()._mouseButtonsPressed.length) {
                getInstance()._mouseButtonsPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < getInstance()._mouseButtonsPressed.length) {
                getInstance()._mouseButtonsPressed[button] = true;
                getInstance()._isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        getInstance()._scrollX = xOffset;
        getInstance()._scrollY = yOffset;
    }

    public static void endFrame() {
        getInstance()._scrollX = 0.0;
        getInstance()._scrollY = 0.0;
        getInstance()._lastX = getInstance()._xPos;
        getInstance()._lastY = getInstance()._yPos;
    }

    public static float getX() {
        return (float) getInstance()._xPos;
    }

    public static double getY() {
        return (float) getInstance()._yPos;
    }

    public static float getDeltaX() {
        return (float) (getInstance()._lastX - getInstance()._xPos);
    }

    public static float getDeltaY() {
        return (float) (getInstance()._lastY - getInstance()._yPos);
    }

    public static float getScrollX() {
        return (float) getInstance()._scrollX;
    }

    public static float getScrollY() {
        return (float) getInstance()._scrollY;
    }

    public static boolean isDragging() {
        return getInstance()._isDragging;
    }

    public static boolean isMouseButtonDown(int button) {
        if (button < getInstance()._mouseButtonsPressed.length) {
            return getInstance()._mouseButtonsPressed[button];
        }
        return false;
    }
}
