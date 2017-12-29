package piengine.visual.render.domain.fragment.handler;

import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_BIND_FRAME_BUFFER;

@Component
public class DoBindFrameBufferFragmentHandler extends FragmentHandler<FrameBuffer> {

    private final FrameBufferService frameBufferService;

    @Wire
    public DoBindFrameBufferFragmentHandler(final FrameBufferService frameBufferService) {
        this.frameBufferService = frameBufferService;
    }

    @Override
    public void handle(final RenderContext context, final FrameBuffer frameBuffer) {
        frameBufferService.bind(frameBuffer);
    }

    @Override
    public RenderFragmentType getType() {
        return DO_BIND_FRAME_BUFFER;
    }
}
