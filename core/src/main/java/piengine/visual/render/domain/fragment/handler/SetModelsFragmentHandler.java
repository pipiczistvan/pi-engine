package piengine.visual.render.domain.fragment.handler;

import piengine.object.model.domain.Model;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import java.util.List;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_MODELS;

@Component
public class SetModelsFragmentHandler extends FragmentHandler<List<Model>> {

    @Override
    public void handle(final RenderContext context, final List<Model> models) {
        context.models.addAll(models);
    }

    @Override
    public RenderFragmentType getType() {
        return SET_MODELS;
    }

}
