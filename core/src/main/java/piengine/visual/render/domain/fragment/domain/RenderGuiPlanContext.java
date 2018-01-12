package piengine.visual.render.domain.fragment.domain;

import org.joml.Vector2i;
import piengine.object.canvas.domain.Canvas;
import piengine.visual.render.domain.context.RenderContext;
import piengine.visual.render.domain.plan.PlanContext;
import piengine.visual.writing.text.domain.Text;

import java.util.List;

public class RenderGuiPlanContext implements PlanContext, RenderContext {

    public final Vector2i viewport;
    public final List<Canvas> canvases;
    public final List<Text> texts;

    public RenderGuiPlanContext(final Vector2i viewport, final List<Canvas> canvases, final List<Text> texts) {
        this.viewport = viewport;
        this.canvases = canvases;
        this.texts = texts;
    }
}
