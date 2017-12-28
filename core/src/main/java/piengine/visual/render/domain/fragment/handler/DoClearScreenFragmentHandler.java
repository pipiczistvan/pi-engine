package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.ClearScreenRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.DO_CLEAR_SCREEN;

@Component
public class DoClearScreenFragmentHandler extends FragmentHandler {

    private final ClearScreenRenderService clearScreenRenderService;

    @Wire
    public DoClearScreenFragmentHandler(final ClearScreenRenderService clearScreenRenderService) {
        this.clearScreenRenderService = clearScreenRenderService;
    }

    @Override
    public void handle(final RenderContext context, final Object o) {
        clearScreenRenderService.clearScreen(context.clearColor);
        clearScreenRenderService.setViewport(context.viewport);
    }

    @Override
    public void validate(final RenderContext context, final Object o) {
        check("clearColor", notNull(context.clearColor));
        check("viewport", notNull(context.viewport));
    }

    @Override
    public RenderFragmentType getType() {
        return DO_CLEAR_SCREEN;
    }

}
