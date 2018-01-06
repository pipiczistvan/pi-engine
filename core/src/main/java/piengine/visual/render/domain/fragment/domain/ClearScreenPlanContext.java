package piengine.visual.render.domain.fragment.domain;

import piengine.core.base.type.color.Color;
import piengine.visual.render.domain.plan.PlanContext;

public class ClearScreenPlanContext implements PlanContext {

    public final Color color;

    public ClearScreenPlanContext(final Color color) {
        this.color = color;
    }
}
