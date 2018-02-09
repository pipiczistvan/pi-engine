package piengine.visual.display.domain.awt;

import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.time.service.TimeService;
import piengine.visual.display.domain.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

public class AwtFrame extends Display {

    private final TimeService timeService;
    private final JFrame frame;
    private final AwtCanvas canvas;

    private final Vector2i oldWindowSize;
    private final Vector2i windowSize;
    private final Vector2i oldViewport;
    private final Vector2i viewport;
    private final Vector2f viewportCenter;

    public AwtFrame(final TimeService timeService, final JFrame frame, final AwtCanvas canvas) {
        this.timeService = timeService;
        this.frame = frame;
        this.canvas = canvas;
        this.oldWindowSize = new Vector2i();
        this.windowSize = new Vector2i();
        this.oldViewport = new Vector2i();
        this.viewport = new Vector2i();
        this.viewportCenter = new Vector2f();

        this.frame.setTitle(get(WINDOW_TITLE));
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        this.frame.setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.transferFocus();
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                canvas.sendCloseSignal();
            }
        });

        this.canvas.initialize(viewport, oldViewport, viewportCenter, eventMap);

        Toolkit.getDefaultToolkit().setDynamicLayout(false);
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

    // Pointer

    @Override
    public Vector2f getPointer() {
        //todo: implement
        return new Vector2f();
    }

    @Override
    public void setPointer(final Vector2f position) {
        //todo: implement
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
}
