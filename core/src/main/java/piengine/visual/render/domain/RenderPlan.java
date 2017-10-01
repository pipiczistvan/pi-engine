package piengine.visual.render.domain;

import piengine.visual.render.domain.fragment.RenderFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderPlan {

    public final List<RenderFragment> fragments = new ArrayList<>();

}
