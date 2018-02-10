package piengine.visual.display.domain.awt;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.event.Action;
import piengine.core.base.event.Event;
import piengine.core.base.exception.PIEngineException;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.Display;
import piutils.map.ListMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

public class AwtFrame extends Display {

    private final TimeService timeService;
    private final AwtCanvas canvas;
    private final Robot robot;

    private final WindowAdapter windowAdapter;
    private final KeyAdapter keyAdapter;
    private final MouseWheelAdapter mouseWheelAdapter;
    private final MouseButtonAdapter mouseButtonAdapter;
    private final MouseMoveAdapter mouseMoveAdapter;
    private final WindowResizedAdapter windowResizedAdapter;

    private final Vector2i oldWindowSize = new Vector2i();
    private final Vector2i windowSize = new Vector2i();
    private final Vector2i contentSize = new Vector2i();
    private final Vector2i oldViewport = new Vector2i();
    private final Vector2i viewport = new Vector2i();
    private final Vector2f viewportCenter = new Vector2f();

    public AwtFrame(final TimeService timeService, final JFrame frame, final AwtCanvas canvas,
                    final ListMap<Integer, Event> releaseEventMap, final ListMap<Integer, Event> pressEventMap,
                    final List<Action<Vector2f>> cursorEvents, final List<Action<Vector2f>> scrollEvents) {
        super(releaseEventMap, pressEventMap, cursorEvents, scrollEvents);
        this.timeService = timeService;
        this.canvas = canvas;
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new PIEngineException(e, "Could not create robot!");
        }

        this.windowAdapter = new WindowAdapter();
        this.keyAdapter = new KeyAdapter(releaseEventMap, pressEventMap);
        this.mouseWheelAdapter = new MouseWheelAdapter(scrollEvents);
        this.mouseButtonAdapter = new MouseButtonAdapter(releaseEventMap, pressEventMap);
        this.mouseMoveAdapter = new MouseMoveAdapter(cursorEvents);
        this.windowResizedAdapter = new WindowResizedAdapter();

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
        //todo: implement
    }

    @Override
    public void setCursorVisibility(final boolean visible) {
        //todo: implement
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

    private void initializeFrame(final JFrame frame) {
        frame.setTitle(get(WINDOW_TITLE));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        frame.setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));
        frame.pack();
        frame.setVisible(true);
        frame.transferFocus();

        frame.addComponentListener(windowResizedAdapter);
        frame.addWindowListener(windowAdapter);

        Toolkit.getDefaultToolkit().setDynamicLayout(false);
    }

    private void initializeCanvas(final AwtCanvas canvas) {
        canvas.setFocusable(true);

        canvas.addMouseMotionListener(mouseMoveAdapter);
        canvas.addMouseWheelListener(mouseWheelAdapter);
        canvas.addMouseListener(mouseButtonAdapter);
        canvas.addKeyListener(keyAdapter);

        canvas.initialize(viewport, oldViewport, viewportCenter, windowSize, contentSize, eventMap);
    }

    private class WindowResizedAdapter extends ComponentAdapter{

        @Override
        public void componentResized(final ComponentEvent e) {
            JFrame frame = (JFrame) e.getComponent();

            oldWindowSize.set(windowSize);
            windowSize.set(frame.getWidth(), e.getComponent().getHeight());
            contentSize.set(frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
        }
    }

    private class WindowAdapter extends java.awt.event.WindowAdapter {

        @Override
        public void windowClosing(final WindowEvent e) {
            canvas.sendCloseSignal();
        }
    }
}
