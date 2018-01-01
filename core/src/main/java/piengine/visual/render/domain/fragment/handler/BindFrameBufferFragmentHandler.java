package piengine.visual.render.domain.fragment.handler;

import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;

@Component
public class BindFrameBufferFragmentHandler extends FragmentHandler<FrameBuffer> {

    private final FrameBufferService frameBufferService;

    @Wire
    public BindFrameBufferFragmentHandler(final FrameBufferService frameBufferService) {
        this.frameBufferService = frameBufferService;
    }

    @Override
    public void handle(final FrameBuffer frameBuffer) {
        if (frameBuffer != null) {
            frameBufferService.bind(frameBuffer);
        } else {
            frameBufferService.unbind();
        }
    }

    @Override
    public RenderFragmentType getType() {
        return BIND_FRAME_BUFFER;
    }
}
