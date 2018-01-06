package piengine.visual.render.domain.fragment.domain;

import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.render.domain.plan.PlanContext;

public class BindFrameBufferPlanContext implements PlanContext {

    public final FrameBuffer frameBuffer;

    public BindFrameBufferPlanContext(final FrameBuffer frameBuffer) {
        this.frameBuffer = frameBuffer;
    }

    public BindFrameBufferPlanContext() {
        this.frameBuffer = null;
    }
}
