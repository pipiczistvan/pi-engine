package piengine.visual.render.domain.fragment.domain;

import org.joml.Vector2i;
import piengine.object.model.domain.Model;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.domain.plan.PlanContext;
import piengine.visual.writing.text.domain.Text;

import java.util.List;

public class RenderGuiPlanContext implements PlanContext, RenderContext {

    public final Vector2i viewport;
    public final List<Model> models;
    public final List<Text> texts;

    public RenderGuiPlanContext(final Vector2i viewport, final List<Model> models, final List<Text> texts) {
        this.viewport = viewport;
        this.models = models;
        this.texts = texts;
    }
}
