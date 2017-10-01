package piengine.visual.render.domain.config;

public class RenderConfig {

    public final boolean depthTest;
    public final boolean blendTest;
    public final boolean wireFrameMode;
    public final int cullFace;
    public final int drawMode;
    public final RenderFunction renderFunction;

    RenderConfig(final boolean depthTest, final boolean blendTest,
                 final boolean wireFrameMode, final int cullFace,
                 final int drawMode, final RenderFunction renderFunction) {
        this.depthTest = depthTest;
        this.blendTest = blendTest;
        this.wireFrameMode = wireFrameMode;
        this.cullFace = cullFace;
        this.drawMode = drawMode;
        this.renderFunction = renderFunction;
    }
}
