package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import javax.swing.*;
import java.awt.*;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MAJOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MINOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;

public class Frame extends Display {

    private final DisplayInterpreter interpreter;
    private final JFrame frame;
    private final AWTGLCanvas canvas;

    public Frame(final DisplayInterpreter interpreter) {
        this.frame = new JFrame();
        this.canvas = new AwtCanvas(createGlData());
        this.interpreter = interpreter;
    }

    @Override
    public void initialize() {
        frame.setTitle(get(WINDOW_TITLE));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(600, 600));

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.transferFocus();

        canvas.render();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void terminate() {

    }

    @Override
    protected void render() {
        canvas.render();
    }

    @Override
    protected boolean shouldUpdate() {
        return true;
    }

    @Override
    protected boolean isResized() {
        return false;
    }

    @Override
    protected void resize() {

    }

    @Override
    protected Vector2f getPointer() {
        return new Vector2f(0);
    }

    @Override
    protected void setPointer(Vector2f position) {

    }

    @Override
    protected Vector2f getDisplayCenter() {
        return new Vector2f(0);
    }

    @Override
    protected void closeDisplay() {

    }

    @Override
    protected int getWidth() {
        return 0;
    }

    @Override
    protected int getHeight() {
        return 0;
    }

    @Override
    protected int getOldWidth() {
        return 0;
    }

    @Override
    protected int getOldHeight() {
        return 0;
    }

    @Override
    protected void setCursorVisibility(boolean visible) {

    }

    private GLData createGlData() {
        GLData glData = new GLData();

        glData.samples = 4;
        glData.swapInterval = 1;
        glData.majorVersion = get(DISPLAY_MAJOR_VERSION);
        glData.minorVersion = get(DISPLAY_MINOR_VERSION);
        glData.profile = GLData.Profile.CORE;
        glData.forwardCompatible = true;

        return glData;
    }

    public class AwtCanvas extends AWTGLCanvas {

        private AwtCanvas(final GLData data) {
            super(data);
        }

        @Override
        public void initGL() {
            interpreter.hey();
        }

        @Override
        public void paintGL() {
            this.swapBuffers();
            this.repaint();
        }
    }
}
