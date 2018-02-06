package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.BindFrameBufferPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;

@Component
public class BindFrameBufferFragmentHandler implements FragmentHandler<BindFrameBufferPlanContext> {

    @Override
    public void handle(final BindFrameBufferPlanContext context) {
        context.framebuffer.bind();
    }

    @Override
    public RenderFragmentType getType() {
        return BIND_FRAME_BUFFER;
    }
}
