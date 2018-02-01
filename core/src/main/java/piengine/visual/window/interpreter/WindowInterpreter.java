package piengine.visual.window.interpreter;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.service.InputService;
import piengine.visual.window.domain.WindowEventType;
import piutils.map.ListMap;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;
import static piengine.visual.window.domain.WindowEventType.CLOSE;
import static piengine.visual.window.domain.WindowEventType.INITIALIZE;
import static piengine.visual.window.domain.WindowEventType.RESIZE;
import static piengine.visual.window.domain.WindowEventType.UPDATE;

@Component
public class WindowInterpreter {

    private final InputService inputService;
    private final ListMap<WindowEventType, Event> eventMap;
    private final GLFWWindowSizeCallback windowSizeCallback;

    private long windowId;
    private long cursorId;
    private IntBuffer windowWidth = BufferUtils.createIntBuffer(1);
    private IntBuffer windowHeight = BufferUtils.createIntBuffer(1);
    private IntBuffer frameBufferWidth = BufferUtils.createIntBuffer(1);
    private IntBuffer frameBufferHeight = BufferUtils.createIntBuffer(1);
    private DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
    private DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
    private Vector2f windowCenter;
    private boolean resized = false;

    @Wire
    public WindowInterpreter(final InputService inputService) {
        this.inputService = inputService;
        this.eventMap = new ListMap<>();
        this.windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                resized = true;
            }
        };
    }

    public void createWindow(String title, int width, int height, boolean fullScreen, int multiSampleCount, boolean cursorHidden, int major, int minor) {
        setupGLFW();
        setupHints(multiSampleCount, major, minor);
        setupContext(title, width, height, fullScreen, cursorHidden);

        initializeWindow();
        updateWindow();
        terminateWindow();
    }

    public void addEvent(WindowEventType type, Event event) {
        eventMap.put(type, event);
    }

    public Vector2f getPointer() {
        xBuffer.clear();
        yBuffer.clear();
        glfwGetCursorPos(windowId, xBuffer, yBuffer);

        return new Vector2f((float) xBuffer.get(), (float) yBuffer.get());
    }

    public void setPointer(Vector2f position) {
        glfwSetCursorPos(windowId, position.x, position.y);
    }

    public Vector2f getWindowCenter() {
        return windowCenter;
    }

    public void closeWindow() {
        glfwSetWindowShouldClose(windowId, GLFW_TRUE);
    }

    public void swapBuffers() {
        glfwSwapBuffers(windowId);
    }

    public int getWidth() {
        return frameBufferWidth.get(0);
    }

    public int getHeight() {
        return frameBufferHeight.get(0);
    }

    private void setupGLFW() {
        if (glfwInit() != GLFW_TRUE) {
            throw new PIEngineException("Unable to initialize GLFW!");
        }
    }

    private void setupHints(int multiSampleCount, int major, int minor) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
//        glfwWindowHint(GLFW_SAMPLES, multiSampleCount);
    }

    private void setupContext(String title, int width, int height, boolean fullScreen, boolean cursorHidden) {
        windowId = glfwCreateWindow(width, height, title, fullScreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        if (windowId == NULL) {
            throw new PIEngineException("Failed to create the GLFW window");
        }
        cursorId = glfwCreateStandardCursor(GLFW_HAND_CURSOR);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        updateSizeBuffers();
        glfwSetWindowPos(
                windowId,
                (vidMode.width() - windowWidth.get()) / 2,
                (vidMode.height() - windowHeight.get()) / 2
        );
        windowCenter = new Vector2f(width / 2, height / 2);

        glfwSetCursor(windowId, cursorId);

        if (cursorHidden) {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        }

        glfwSetKeyCallback(windowId, inputService.getKeyCallback());
        glfwSetMouseButtonCallback(windowId, inputService.getMouseButtonCallback());
        glfwSetCursorPosCallback(windowId, inputService.getCursorPosCallback());
        glfwSetWindowSizeCallback(windowId, windowSizeCallback);

        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(1);
        glfwShowWindow(windowId);
        createCapabilities();
    }

    private void initializeWindow() {
        eventMap.get(INITIALIZE).forEach(Event::invoke);
    }

    private void updateWindow() {
        while (glfwWindowShouldClose(windowId) != GLFW_TRUE) {
            glfwPollEvents();
            if (resized) {
                updateSizeBuffers();
                eventMap.get(RESIZE).forEach(Event::invoke);
                resized = false;
            }
            eventMap.get(UPDATE).forEach(Event::invoke);
        }
    }

    private void terminateWindow() {
        eventMap.get(CLOSE).forEach(Event::invoke);

        glfwDestroyWindow(windowId);
        glfwTerminate();
        glfwSetErrorCallback(null);
        System.exit(0);
    }

    private void updateSizeBuffers() {
        windowWidth.clear();
        windowHeight.clear();
        frameBufferWidth.clear();
        frameBufferHeight.clear();
        glfwGetFramebufferSize(windowId, frameBufferWidth, frameBufferHeight);
        glfwGetWindowSize(windowId, windowWidth, windowHeight);
    }
}
