package piengine.visual.render.domain.fragment.handler;

import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_UNBIND_FRAME_BUFFER;

@Component
public class DoUnbindFrameBufferFragmentHandler extends FragmentHandler {

    private final FrameBufferService frameBufferService;

    @Wire
    public DoUnbindFrameBufferFragmentHandler(final FrameBufferService frameBufferService) {
        this.frameBufferService = frameBufferService;
    }

    @Override
    public void handle(final RenderContext context, final Object o) {
        frameBufferService.unbind();
    }

    @Override
    public RenderFragmentType getType() {
        return DO_UNBIND_FRAME_BUFFER;
    }
}
