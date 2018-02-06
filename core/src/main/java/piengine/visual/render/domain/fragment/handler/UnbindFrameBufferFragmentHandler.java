package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.BindFrameBufferPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.UNBIND_FRAME_BUFFER;

@Component
public class UnbindFrameBufferFragmentHandler implements FragmentHandler<BindFrameBufferPlanContext> {

    @Override
    public void handle(final BindFrameBufferPlanContext context) {
        context.framebuffer.unbind();
    }

    @Override
    public RenderFragmentType getType() {
        return UNBIND_FRAME_BUFFER;
    }
}
