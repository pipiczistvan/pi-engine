package piengine.visual.render.domain.plan;

import piengine.visual.render.domain.fragment.RenderFragment;

import java.util.List;

public class RenderPlan {

    public final List<RenderFragment> fragments;

    RenderPlan(final List<RenderFragment> fragments) {
        this.fragments = fragments;
    }
}
