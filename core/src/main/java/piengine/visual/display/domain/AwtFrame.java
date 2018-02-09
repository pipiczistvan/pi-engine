package piengine.visual.display.domain;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import piengine.core.base.event.Event;
import piengine.core.time.service.TimeService;

import javax.swing.*;
import java.awt.*;

import static org.lwjgl.opengl.GL.createCapabilities;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MAJOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MINOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;
import static piengine.visual.display.domain.DisplayEventType.INITIALIZE;
import static piengine.visual.display.domain.DisplayEventType.RENDER;
import static piengine.visual.display.domain.DisplayEventType.UPDATE;

public class AwtFrame extends Display {

    private final TimeService timeService;
    private final JFrame frame;
    private final AWTGLCanvas canvas;
    private final Vector2i viewport;

    public AwtFrame(final TimeService timeService) {
        this.timeService = timeService;
        this.frame = new JFrame();
        this.canvas = new AwtCanvas(createGlData());
        this.viewport = new Vector2i();
    }

    // Interventions

    @Override
    public void startLoop() {
        createFrame();

        while (frame.isActive()) {
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
        //todo: implement
        return new Vector2i();
    }

    @Override
    public Vector2i getOldWindowSize() {
        //todo: implement
        return new Vector2i();
    }

    @Override
    public Vector2i getOldViewport() {
        //todo: implement
        return new Vector2i();
    }

    @Override
    public Vector2f getViewportCenter() {
        //todo: implement
        return new Vector2f();
    }

    @Override
    public Vector2i getViewport() {
        return viewport;
    }

    private void createFrame() {
        frame.setTitle(get(WINDOW_TITLE));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));

        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.transferFocus();

        canvas.render();
    }

    private GLData createGlData() {
        GLData glData = new GLData();

        glData.samples = 4;
        glData.swapInterval = 0;
        glData.majorVersion = get(DISPLAY_MAJOR_VERSION);
        glData.minorVersion = get(DISPLAY_MINOR_VERSION);
        glData.profile = GLData.Profile.CORE;
        glData.forwardCompatible = true;

        return glData;
    }

    private class AwtCanvas extends AWTGLCanvas {

        private AwtCanvas(final GLData data) {
            super(data);
        }

        @Override
        public void initGL() {
            viewport.set(getWidth(), getHeight());
            createCapabilities(true);
            eventMap.get(INITIALIZE).forEach(Event::invoke);
        }

        @Override
        public void paintGL() {
            viewport.set(getWidth(), getHeight());
            eventMap.get(UPDATE).forEach(Event::invoke);
            eventMap.get(RENDER).forEach(Event::invoke);
            this.swapBuffers();
        }
    }
}
