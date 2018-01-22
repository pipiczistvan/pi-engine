package piengine.visual.render.domain.fragment.handler;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.render.domain.fragment.domain.BindFrameBufferPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;

@Component
public class BindFrameBufferFragmentHandler implements FragmentHandler<BindFrameBufferPlanContext> {

    private final FramebufferService framebufferService;

    private static Framebuffer fbo;

    @Wire
    public BindFrameBufferFragmentHandler(final FramebufferService framebufferService) {
        this.framebufferService = framebufferService;
    }

    @Override
    public void handle(final BindFrameBufferPlanContext context) {
        if (context.framebuffer != null) {
            framebufferService.bind(context.framebuffer);
            fbo = context.framebuffer;
        } else {
            framebufferService.unbind();
        }
    }

    @Override
    public RenderFragmentType getType() {
        return BIND_FRAME_BUFFER;
    }
}
