package piengine.visual.render.domain.fragment;

import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.plan.PlanContext;

public class RenderFragment<T extends PlanContext> {

    public final RenderFragmentType type;
    public final T context;

    public RenderFragment(final RenderFragmentType type, final T context) {
        this.type = type;
        this.context = context;
    }
}
