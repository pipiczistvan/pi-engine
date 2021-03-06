package piengine.visual.display.domain.glfw;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.domain.Key;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.Display;

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
import static piengine.visual.display.domain.DisplayEventType.CLOSE;
import static piengine.visual.display.domain.DisplayEventType.INITIALIZE;
import static piengine.visual.display.domain.DisplayEventType.RENDER;
import static piengine.visual.display.domain.DisplayEventType.RESIZE;
import static piengine.visual.display.domain.DisplayEventType.UPDATE;

public class GlfwWindow extends Display {

    private final TimeService timeService;

    private final GLFWWindowSizeCallback windowSizeCallback;
    private final KeyCallback keyCallback;
    private final MouseButtonCallback mouseButtonCallback;
    private final CursorPosCallback cursorPosCallback;
    private final ScrollCallback scrollCallback;

    private final Vector2i oldWindowSize = new Vector2i();
    private final Vector2i windowSize = new Vector2i();
    private final Vector2i oldViewport = new Vector2i();
    private final Vector2i viewport = new Vector2i();
    private final Vector2f viewportCenter = new Vector2f();
    private final Vector2f windowPosition = new Vector2f();

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

    public GlfwWindow(final TimeService timeService) {
        this.timeService = timeService;
        this.windowSizeCallback = new WindowSizeCallback();
        this.keyCallback = new KeyCallback(releaseEventMap, pressEventMap);
        this.mouseButtonCallback = new MouseButtonCallback(releaseEventMap, pressEventMap);
        this.cursorPosCallback = new CursorPosCallback(cursorEvents);
        this.scrollCallback = new ScrollCallback(scrollEvents);
    }

    // Interventions

    @Override
    public void startLoop() {
        createGlfwWindow();
        eventMap.get(INITIALIZE).forEach(Event::invoke);

        while (!glfwWindowShouldClose(windowId)) {
            timeService.update();

            if (timeService.waitTimeSpent()) {
                if (resized) {
                    updateSizeBuffers();
                    eventMap.get(RESIZE).forEach(Event::invoke);
                    resized = false;
                }

                glfwPollEvents();

                eventMap.get(UPDATE).forEach(Event::invoke);
                eventMap.get(RENDER).forEach(Event::invoke);

                glfwSwapBuffers(windowId);

                timeService.frameUpdated();
            }
        }

        eventMap.get(CLOSE).forEach(Event::invoke);
        glfwDestroyWindow(windowId);
        glfwTerminate();
        glfwSetErrorCallback(null);
        System.exit(0);
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
    public void closeDisplay() {
        glfwSetWindowShouldClose(windowId, true);
    }

    @Override
    public Vector2f getPointer() {
        xBuffer.clear();
        yBuffer.clear();
        glfwGetCursorPos(windowId, xBuffer, yBuffer);

        return new Vector2f((float) xBuffer.get(), (float) yBuffer.get());
    }

    @Override
    public void setPointer(final Vector2f position) {
        glfwSetCursorPos(windowId, position.x, position.y);
    }

    // Window and viewport

    @Override
    public Vector2i getWindowSize() {
        return windowSize;
    }

    @Override
    public Vector2i getOldWindowSize() {
        return oldWindowSize;
    }

    @Override
    public Vector2i getViewport() {
        return viewport;
    }

    @Override
    public Vector2i getOldViewport() {
        return oldViewport;
    }

    @Override
    public Vector2f getViewportCenter() {
        return viewportCenter;
    }

    @Override
    protected int keyToCode(final Key key) {
        return key.getGlfwCode();
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

        glfwSetKeyCallback(windowId, keyCallback);
        glfwSetMouseButtonCallback(windowId, mouseButtonCallback);
        glfwSetCursorPosCallback(windowId, cursorPosCallback);
        glfwSetScrollCallback(windowId, scrollCallback);
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
        oldViewport.set(viewport);
        viewport.set(frameBufferWidth.get(), frameBufferHeight.get());
        windowPosition.set(windowPosX.get(), windowPosY.get());
        viewportCenter.set(viewport.x / 2f, viewport.y / 2f);
    }

    private class WindowSizeCallback extends GLFWWindowSizeCallback {

        @Override
        public void invoke(final long window, final int width, final int height) {
            if (width != 0 && height != 0) {
                resized = true;
            }
        }
    }
}
