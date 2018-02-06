package piengine.visual.render.domain.fragment.domain;

import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.render.domain.plan.PlanContext;

public class BindFrameBufferPlanContext implements PlanContext {

    public final Framebuffer framebuffer;

    public BindFrameBufferPlanContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    public BindFrameBufferPlanContext() {
        this.framebuffer = null;
    }
}
