package piengine.visual.render.domain.plan;

import piengine.core.base.type.color.Color;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.render.domain.fragment.RenderFragment;
import piengine.visual.render.domain.fragment.domain.BindFrameBufferPlanContext;
import piengine.visual.render.domain.fragment.domain.ClearScreenPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;

import java.util.ArrayList;
import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.BIND_FRAME_BUFFER;
import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.CLEAR_SCREEN;

public abstract class RenderPlanBuilder<B extends RenderPlanBuilder<B, C>, C extends PlanContext> {

    protected final C context;
    private final List<RenderFragment> fragments = new ArrayList<>();

    protected RenderPlanBuilder(final C context) {
        this.context = context;
    }

    public B clearScreen(final Color color) {
        fragments.add(new RenderFragment<>(CLEAR_SCREEN, new ClearScreenPlanContext(color)));
        return thiz();
    }

    public B bindFrameBuffer(final Framebuffer framebuffer, final RenderPlan renderPlan) {
        fragments.add(new RenderFragment<>(BIND_FRAME_BUFFER, new BindFrameBufferPlanContext(framebuffer)));
        fragments.addAll(renderPlan.fragments);
        fragments.add(new RenderFragment<>(BIND_FRAME_BUFFER, new BindFrameBufferPlanContext()));
        return thiz();
    }

    public RenderPlan render() {
        fragments.add(new RenderFragment<>(getType(), context));
        return new RenderPlan(fragments);
    }

    protected abstract B thiz();

    protected abstract RenderFragmentType getType();
}
