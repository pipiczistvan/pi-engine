package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.plan.PlanContext;

public interface FragmentHandler<T extends PlanContext> {

    void handle(final T context);

    RenderFragmentType getType();
}
