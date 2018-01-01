package piengine.visual.render.domain.fragment.handler;

import piengine.object.model.domain.Model;
import piengine.visual.render.domain.context.WorldRenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.WorldRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TO_WORLD;

@Component
public class RenderToWorldFragmentHandler extends FragmentHandler<WorldRenderContext> {

    private final WorldRenderService renderService;

    @Wire
    public RenderToWorldFragmentHandler(final WorldRenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void handle(final WorldRenderContext context) {
        renderService.process(context);
    }

    @Override
    public void validate(final WorldRenderContext context) {
        check("camera", notNull(context.camera));
        check("light", notNull(context.light));
        check("models", notNull(context.models));
        for (Model model : context.models) {
            check("model", notNull(model));
        }
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_TO_WORLD;
    }
}
