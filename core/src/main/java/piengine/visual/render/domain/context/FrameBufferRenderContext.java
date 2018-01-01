package piengine.visual.render.domain.context;

import piengine.visual.framebuffer.domain.FrameBuffer;

public class FrameBufferRenderContext implements RenderContext {

    public final FrameBuffer frameBuffer;
    public final RenderContext renderContext;

    public FrameBufferRenderContext(final FrameBuffer frameBuffer, final RenderContext renderContext) {
        this.frameBuffer = frameBuffer;
        this.renderContext = renderContext;
    }
}
