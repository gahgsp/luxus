package luxus;

import luxus.listener.KeyboardListener;
import luxus.listener.MouseListener;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static Window singletonInstance = null;
    private static Scene currentScene = null;

    private int _width, _height;
    private String _title;
    private long _glfwWindow;

    float r, g, b, a = 0f;

    private Window() {
        this._width = 1920;
        this._height = 1080;
        this._title = "Welcome to the Luxus Engine!";

        r = 1f;
        g = 1f;
        b = 1f;
        a = 1f;
    }

    public static Window getInstance() {
        if (Window.singletonInstance == null) {
            Window.singletonInstance = new Window();
        }
        return Window.singletonInstance;
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknown Scene '" + newScene + "'!";
        }
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory once the loop is exited.
        glfwFreeCallbacks(_glfwWindow);
        glfwDestroyWindow(_glfwWindow);

        // Terminate the GLFW and free the terminal error callback.
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Directs all the errors to the err default console.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initializing GLFW.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        // Configuring GLFW.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window.
        _glfwWindow = glfwCreateWindow(this._width, this._height, this._title, NULL, NULL);
        if (_glfwWindow == NULL) {
            throw new RuntimeException("Failed to create the GLFW window!");
        }

        // Registering the mouse listener.
        glfwSetCursorPosCallback(_glfwWindow, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(_glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(_glfwWindow, MouseListener::mouseScrollCallback);

        // Registering keyboard listener.
        glfwSetKeyCallback(_glfwWindow, KeyboardListener::keyCallback);

        // Creating the OpenGL current context.
        glfwMakeContextCurrent(_glfwWindow);

        // Enabling / Disabling V-Sync.
        glfwSwapInterval(0);

        // Making the Window visible.
        glfwShowWindow(_glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        changeScene(0);
    }

    private void loop() {
        float frameBeginTime = (float) glfwGetTime();
        float frameEndTime;
        float deltaTime = -1f;

        while (!glfwWindowShouldClose(_glfwWindow)) {
            // Poll the events.
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (deltaTime >= 0) {
                currentScene.update(deltaTime);
            }

            glfwSwapBuffers(_glfwWindow);

            frameEndTime = (float) glfwGetTime();
            deltaTime = frameEndTime - frameBeginTime;
            frameBeginTime = frameEndTime;
        }
    }
}
