package piengine.visual.render.domain.fragment.handler;

import piengine.object.model.domain.Model;
import piengine.visual.render.domain.context.GuiRenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.GuiRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TO_GUI;

@Component
public class RenderToGuiFragmentHandler extends FragmentHandler<GuiRenderContext> {

    private final GuiRenderService renderService;

    @Wire
    public RenderToGuiFragmentHandler(final GuiRenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void handle(final GuiRenderContext context) {
        renderService.process(context);
    }

    @Override
    public void validate(final GuiRenderContext context) {
        check("viewport", notNull(context.viewport));
        check("models", notNull(context.models));
        for (Model model : context.models) {
            check("model", notNull(model));
        }
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_TO_GUI;
    }
}
