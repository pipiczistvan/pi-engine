package piengine.visual.render.domain.config;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static piengine.visual.render.domain.config.RenderFunction.DRAW_ELEMENTS;

public class RenderConfigBuilder {

    private boolean depthTest = true;
    private boolean blendTest = false;
    private boolean wireFrameMode = false;
    private int cullFace = GL_BACK;
    private int drawMode = GL_TRIANGLES;
    private RenderFunction renderFunction = DRAW_ELEMENTS;

    private RenderConfigBuilder() {
    }

    public static RenderConfigBuilder create() {
        return new RenderConfigBuilder();
    }

    public RenderConfigBuilder withDepthTest(final boolean depthTest) {
        this.depthTest = depthTest;
        return this;
    }

    public RenderConfigBuilder withBlendTest(final boolean blendTest) {
        this.blendTest = blendTest;
        return this;
    }

    public RenderConfigBuilder withWireFrameMode(final boolean wireFrameMode) {
        this.wireFrameMode = wireFrameMode;
        return this;
    }

    public RenderConfigBuilder withCullFace(final int cullFace) {
        this.cullFace = cullFace;
        return this;
    }

    public RenderConfigBuilder withDrawMode(final int drawMode) {
        this.drawMode = drawMode;
        return this;
    }

    public RenderConfigBuilder withRenderFunction(final RenderFunction renderFunction) {
        this.renderFunction = renderFunction;
        return this;
    }

    public RenderConfig build() {
        return new RenderConfig(depthTest, blendTest, wireFrameMode, cullFace, drawMode, renderFunction);
    }
}
