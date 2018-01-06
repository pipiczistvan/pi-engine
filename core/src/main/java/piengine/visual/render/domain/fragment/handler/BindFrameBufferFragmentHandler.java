package piengine.visual.render.domain.fragment.handler;

import piengine.visual.framebuffer.service.FrameBufferService;
import piengine.visual.render.domain.fragment.domain.BindFrameBufferPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;

@Component
public class BindFrameBufferFragmentHandler implements FragmentHandler<BindFrameBufferPlanContext> {

    private final FrameBufferService frameBufferService;

    @Wire
    public BindFrameBufferFragmentHandler(final FrameBufferService frameBufferService) {
        this.frameBufferService = frameBufferService;
    }

    @Override
    public void handle(final BindFrameBufferPlanContext context) {
        if (context.frameBuffer != null) {
            frameBufferService.bind(context.frameBuffer);
        } else {
            frameBufferService.unbind();
        }
    }

    @Override
    public RenderFragmentType getType() {
        return BIND_FRAME_BUFFER;
    }
}
