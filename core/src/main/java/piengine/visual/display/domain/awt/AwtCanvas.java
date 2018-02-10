package piengine.visual.display.domain.awt;


import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import piengine.core.base.event.Event;
import piengine.visual.display.domain.DisplayEventType;
import piutils.map.ListMap;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static org.lwjgl.opengl.GL.createCapabilities;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MAJOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MINOR_VERSION;
import static piengine.visual.display.domain.DisplayEventType.CLOSE;
import static piengine.visual.display.domain.DisplayEventType.INITIALIZE;
import static piengine.visual.display.domain.DisplayEventType.RENDER;
import static piengine.visual.display.domain.DisplayEventType.RESIZE;
import static piengine.visual.display.domain.DisplayEventType.UPDATE;

public class AwtCanvas extends AWTGLCanvas {

    private ListMap<DisplayEventType, Event> eventMap;
    private Vector2i viewport;
    private Vector2i oldViewport;
    private Vector2f viewportCenter;
    private boolean closed = false;
    private boolean resized = false;

    public AwtCanvas() {
        super(createGlData());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                if (getWidth() != 0 && getHeight() != 0) {
                    resized = true;
                }
            }
        });
    }

    @Override
    public void initGL() {
        updateViewportBuffers();
        createCapabilities(true);
        eventMap.get(INITIALIZE).forEach(Event::invoke);
    }

    @Override
    public void paintGL() {
        if (!closed) {
            if (resized) {
                updateViewportBuffers();
                eventMap.get(RESIZE).forEach(Event::invoke);
                resized = false;
            }

            eventMap.get(UPDATE).forEach(Event::invoke);
            eventMap.get(RENDER).forEach(Event::invoke);
            this.swapBuffers();
        } else {
            eventMap.get(CLOSE).forEach(Event::invoke);
            System.exit(0);
        }
    }

    void sendCloseSignal() {
        this.closed = true;
    }

    void initialize(final Vector2i viewport, final Vector2i oldViewport, final Vector2f viewportCenter, final ListMap<DisplayEventType, Event> eventMap) {
        this.viewport = viewport;
        this.oldViewport = oldViewport;
        this.viewportCenter = viewportCenter;
        this.eventMap = eventMap;
    }

    void updateViewportCenter() {
        Point location = getLocationOnScreen();
        viewportCenter.set(location.x + viewport.x / 2f, location.y + viewport.y / 2f);
    }

    private void updateViewportBuffers() {
        oldViewport.set(viewport);
        viewport.set(getWidth(), getHeight());

        updateViewportCenter();
    }

    private static GLData createGlData() {
        GLData glData = new GLData();

        glData.samples = 4;
        glData.swapInterval = 0;
        glData.majorVersion = get(DISPLAY_MAJOR_VERSION);
        glData.minorVersion = get(DISPLAY_MINOR_VERSION);
        glData.profile = GLData.Profile.CORE;
        glData.forwardCompatible = true;

        return glData;
    }
}
