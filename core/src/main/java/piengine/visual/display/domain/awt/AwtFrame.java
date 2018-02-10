package piengine.visual.display.domain.awt;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.exception.PIEngineException;
import piengine.core.input.domain.Key;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_CURSOR_HIDDEN;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

public class AwtFrame extends Display {

    private final TimeService timeService;
    private final JFrame frame;
    private final AwtCanvas canvas;
    private final Robot robot;

    private final WindowAdapter windowAdapter;
    private final KeyAdapter keyAdapter;
    private final MouseWheelAdapter mouseWheelAdapter;
    private final MouseButtonAdapter mouseButtonAdapter;
    private final MouseMoveAdapter mouseMoveAdapter;
    private final WindowComponentAdapter windowComponentAdapter;

    private final Vector2i oldWindowSize = new Vector2i();
    private final Vector2i windowSize = new Vector2i();
    private final Vector2i oldViewport = new Vector2i();
    private final Vector2i viewport = new Vector2i();
    private final Vector2f viewportCenter = new Vector2f();

    private final Cursor hiddenCursor;

    public AwtFrame(final TimeService timeService, final JFrame frame, final AwtCanvas canvas) {
        this.timeService = timeService;
        this.frame = frame;
        this.canvas = canvas;
        this.robot = createRobot();

        this.windowAdapter = new WindowAdapter();
        this.keyAdapter = new KeyAdapter(releaseEventMap, pressEventMap);
        this.mouseWheelAdapter = new MouseWheelAdapter(scrollEvents);
        this.mouseButtonAdapter = new MouseButtonAdapter(releaseEventMap, pressEventMap);
        this.mouseMoveAdapter = new MouseMoveAdapter(cursorEvents);
        this.windowComponentAdapter = new WindowComponentAdapter();

        this.hiddenCursor = frame.getToolkit().createCustomCursor(new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB), new Point(), "blank");

        setCursorVisibility(!get(WINDOW_CURSOR_HIDDEN));
        initializeFrame(frame);
        initializeCanvas(canvas);
    }

    // Interventions

    @Override
    public void startLoop() {
        canvas.render();

        while (true) {
            timeService.update();

            if (timeService.waitTimeSpent()) {
                canvas.render();
                timeService.frameUpdated();
            }
        }
    }

    @Override
    public void closeDisplay() {
        canvas.sendCloseSignal();
    }

    @Override
    public void setCursorVisibility(final boolean visible) {
        Cursor cursor = visible ? Cursor.getDefaultCursor() : hiddenCursor;

        frame.setCursor(cursor);
    }

    @Override
    public void setPointer(final Vector2f position) {
        robot.mouseMove((int) position.x, (int) position.y);
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
    public Vector2i getOldViewport() {
        return oldViewport;
    }

    @Override
    public Vector2f getViewportCenter() {
        return viewportCenter;
    }

    @Override
    public Vector2i getViewport() {
        return viewport;
    }

    @Override
    protected int keyToCode(final Key key) {
        return key.getAwtCode();
    }

    private Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new PIEngineException(e, "Could not create robot!");
        }
    }

    private void initializeFrame(final JFrame frame) {
        frame.setTitle(get(WINDOW_TITLE));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        frame.setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));
        frame.pack();
        frame.setVisible(true);
        frame.transferFocus();

        frame.addComponentListener(windowComponentAdapter);
        frame.addWindowListener(windowAdapter);

        Toolkit.getDefaultToolkit().setDynamicLayout(false);
    }

    private void initializeCanvas(final AwtCanvas canvas) {
        canvas.setFocusable(true);

        canvas.addMouseMotionListener(mouseMoveAdapter);
        canvas.addMouseWheelListener(mouseWheelAdapter);
        canvas.addMouseListener(mouseButtonAdapter);
        canvas.addKeyListener(keyAdapter);

        canvas.initialize(viewport, oldViewport, viewportCenter, eventMap);
    }

    private class WindowComponentAdapter extends ComponentAdapter {

        @Override
        public void componentResized(final ComponentEvent e) {
            oldWindowSize.set(windowSize);
            windowSize.set(e.getComponent().getWidth(), e.getComponent().getHeight());
        }

        @Override
        public void componentMoved(final ComponentEvent e) {
            canvas.updateViewportCenter();
        }
    }

    private class WindowAdapter extends java.awt.event.WindowAdapter {

        @Override
        public void windowClosing(final WindowEvent e) {
            canvas.sendCloseSignal();
        }
    }
}
