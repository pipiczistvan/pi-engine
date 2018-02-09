package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.service.InputService;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_HAND_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
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
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
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
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeLimits;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MAJOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MINOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_CURSOR_HIDDEN;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_FULL_SCREEN;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_RESIZABLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

public class Window extends Display {

    private final InputService inputService;
    private final GLFWWindowSizeCallback windowSizeCallback;
    private final Vector2i oldWindowSize;
    private final Vector2i windowSize;
    private final Vector2i oldFramebufferSize;
    private final Vector2i framebufferSize;
    private final Vector2f displayCenter;
    private final Vector2f windowPosition;

    private long windowId;
    private long cursorId;
    private IntBuffer windowWidth = BufferUtils.createIntBuffer(1);
    private IntBuffer windowHeight = BufferUtils.createIntBuffer(1);
    private IntBuffer frameBufferWidth = BufferUtils.createIntBuffer(1);
    private IntBuffer frameBufferHeight = BufferUtils.createIntBuffer(1);
    private IntBuffer windowPosX = BufferUtils.createIntBuffer(1);
    private IntBuffer windowPosY = BufferUtils.createIntBuffer(1);
    private DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
    private DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
    private boolean resized = false;

    public Window(final InputService inputService) {
        this.inputService = inputService;
        this.windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                if (width != 0 && height != 0) {
                    resized = true;
                }
            }
        };
        this.oldWindowSize = new Vector2i();
        this.windowSize = new Vector2i();
        this.oldFramebufferSize = new Vector2i();
        this.framebufferSize = new Vector2i();
        this.displayCenter = new Vector2f();
        this.windowPosition = new Vector2f();
    }

    @Override
    public void initialize() {
        createGlfwWindow();
    }

    @Override
    public void update(float delta) {
        glfwPollEvents();
    }

    @Override
    protected boolean isResized() {
        return resized;
    }

    @Override
    protected void resize() {
        updateSizeBuffers();
        resized = false;
    }

    @Override
    protected void render() {
        glfwSwapBuffers(windowId);
    }

    @Override
    public void terminate() {
        glfwDestroyWindow(windowId);
        glfwTerminate();
        glfwSetErrorCallback(null);
        System.exit(0);
    }


    @Override
    public Vector2f getPointer() {
        xBuffer.clear();
        yBuffer.clear();
        glfwGetCursorPos(windowId, xBuffer, yBuffer);

        return new Vector2f((float) xBuffer.get(), (float) yBuffer.get());
    }

    @Override
    public void setPointer(Vector2f position) {
        glfwSetCursorPos(windowId, position.x, position.y);
    }

    @Override
    public Vector2f getDisplayCenter() {
        return displayCenter;
    }

    @Override
    public void closeDisplay() {
        glfwSetWindowShouldClose(windowId, true);
    }

    @Override
    public int getWidth() {
        return framebufferSize.x;
    }

    public int getHeight() {
        return framebufferSize.y;
    }

    public int getOldWidth() {
        return oldFramebufferSize.x;
    }

    @Override
    public int getOldHeight() {
        return oldFramebufferSize.y;
    }

    @Override
    public void setCursorVisibility(final boolean visible) {
        if (visible) {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        } else {
            glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        }
    }

    @Override
    protected boolean shouldUpdate() {
        return !glfwWindowShouldClose(windowId);
    }

    private void createGlfwWindow() {
        if (!glfwInit()) {
            throw new PIEngineException("Unable to initialize GLFW!");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, get(WINDOW_RESIZABLE) ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, get(DISPLAY_MAJOR_VERSION));
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, get(DISPLAY_MINOR_VERSION));
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        windowId = glfwCreateWindow(get(WINDOW_WIDTH), get(WINDOW_HEIGHT), get(WINDOW_TITLE), get(WINDOW_FULL_SCREEN) ? glfwGetPrimaryMonitor() : NULL, NULL);
        if (windowId == NULL) {
            throw new PIEngineException("Failed to create the GLFW display");
        }
        cursorId = glfwCreateStandardCursor(GLFW_HAND_CURSOR);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        updateSizeBuffers();
        glfwSetWindowPos(
                windowId,
                (vidMode.width() - windowSize.x) / 2,
                (vidMode.height() - windowSize.y) / 2
        );
        glfwSetWindowSizeLimits(windowId, get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT), GLFW_DONT_CARE, GLFW_DONT_CARE);

        glfwSetCursor(windowId, cursorId);

        setCursorVisibility(!get(WINDOW_CURSOR_HIDDEN));
        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(1);
        glfwShowWindow(windowId);

        glfwSetKeyCallback(windowId, inputService.getKeyCallback());
        glfwSetMouseButtonCallback(windowId, inputService.getMouseButtonCallback());
        glfwSetCursorPosCallback(windowId, inputService.getCursorPosCallback());
        glfwSetScrollCallback(windowId, inputService.getScrollCallback());
        glfwSetWindowSizeCallback(windowId, windowSizeCallback);

        createCapabilities();
    }

    private void updateSizeBuffers() {
        windowWidth.clear();
        windowHeight.clear();
        frameBufferWidth.clear();
        frameBufferHeight.clear();
        windowPosX.clear();
        windowPosY.clear();
        glfwGetFramebufferSize(windowId, frameBufferWidth, frameBufferHeight);
        glfwGetWindowSize(windowId, windowWidth, windowHeight);
        glfwGetWindowPos(windowId, windowPosX, windowPosY);

        oldWindowSize.set(windowSize);
        windowSize.set(windowWidth.get(), windowHeight.get());
        oldFramebufferSize.set(framebufferSize);
        framebufferSize.set(frameBufferWidth.get(), frameBufferHeight.get());
        windowPosition.set(windowPosX.get(), windowPosY.get());
        displayCenter.set(windowSize.x / 2f, windowSize.y / 2f);
    }
}