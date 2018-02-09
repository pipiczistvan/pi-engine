package piengine.visual.display.interpreter;

import org.joml.Vector2f;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import static org.lwjgl.opengl.GL.createCapabilities;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MAJOR_VERSION;
import static piengine.core.base.type.property.PropertyKeys.DISPLAY_MINOR_VERSION;

public class Canvas extends Display {

    private final AWTGLCanvas canvas;

    public Canvas() {
        this.canvas = new AwtCanvas(createGlData());
    }

    @Override
    protected void render() {
        canvas.render();
    }

    @Override
    protected boolean shouldUpdate() {
        return false;
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
        return null;
    }

    @Override
    protected void setPointer(Vector2f position) {

    }

    @Override
    protected Vector2f getDisplayCenter() {
        return null;
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

        glData.swapInterval = 1;
        glData.majorVersion = get(DISPLAY_MAJOR_VERSION);
        glData.minorVersion = get(DISPLAY_MINOR_VERSION);
        glData.profile = GLData.Profile.CORE;
        glData.forwardCompatible = true;

        return glData;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void terminate() {

    }

    @Override
    public void update(float delta) {

    }

    private class AwtCanvas extends AWTGLCanvas {

        private AwtCanvas(final GLData data) {
            super(data);
        }

        @Override
        public void initGL() {
            createCapabilities();
        }

        @Override
        public void paintGL() {

        }
    }
}
