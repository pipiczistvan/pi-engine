package piengine.visual.render.domain.config;

public class RenderConfig {

    public final boolean depthTest;
    public final boolean depthMask;
    public final boolean blendTest;
    public final boolean wireFrameMode;
    public final boolean clipDistance;
    public final int cullFace;
    public final int drawMode;
    public final RenderFunction renderFunction;

    RenderConfig(final boolean depthTest, final boolean depthMask,
                 final boolean blendTest, final boolean wireFrameMode,
                 final boolean clipDistance, final int cullFace,
                 final int drawMode, final RenderFunction renderFunction) {
        this.depthTest = depthTest;
        this.depthMask = depthMask;
        this.blendTest = blendTest;
        this.wireFrameMode = wireFrameMode;
        this.clipDistance = clipDistance;
        this.cullFace = cullFace;
        this.drawMode = drawMode;
        this.renderFunction = renderFunction;
    }
}
