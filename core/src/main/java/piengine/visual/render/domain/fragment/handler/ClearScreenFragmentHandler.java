package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.ClearScreenPlanContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.ClearScreenRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.CLEAR_SCREEN;

@Component
public class ClearScreenFragmentHandler implements FragmentHandler<ClearScreenPlanContext> {

    private final ClearScreenRenderService renderService;

    @Wire
    public ClearScreenFragmentHandler(final ClearScreenRenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void handle(final ClearScreenPlanContext context) {
        renderService.clearScreen(context.color);
    }

    @Override
    public RenderFragmentType getType() {
        return CLEAR_SCREEN;
    }
}
