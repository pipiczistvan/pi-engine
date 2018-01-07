package piengine.visual.render.domain.plan;

import org.joml.Vector2i;
import piengine.object.model.domain.Model;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.writing.text.domain.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_GUI;

public class GuiRenderPlanBuilder extends RenderPlanBuilder<GuiRenderPlanBuilder, RenderGuiPlanContext> {

    GuiRenderPlanBuilder(final Vector2i viewport) {
        super(new RenderGuiPlanContext(
                viewport,
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    public GuiRenderPlanBuilder loadModels(Model... models) {
        this.context.models.addAll(Arrays.asList(models));
        return this;
    }

    public GuiRenderPlanBuilder loadTexts(Text... texts) {
        this.context.texts.addAll(Arrays.asList(texts));
        return this;
    }

    @Override
    protected piengine.visual.render.domain.plan.GuiRenderPlanBuilder thiz() {
        return this;
    }

    @Override
    protected RenderFragmentType getType() {
        return RENDER_GUI;
    }
}
