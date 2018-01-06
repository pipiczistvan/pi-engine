package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.service.GuiRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class RenderGuiFragmentHandler implements FragmentHandler<RenderGuiPlanContext> {

    private final GuiRenderService guiRenderService;

    @Wire
    public RenderGuiFragmentHandler(final GuiRenderService guiRenderService) {
        this.guiRenderService = guiRenderService;
    }

    @Override
    public void handle(final RenderGuiPlanContext context) {
        guiRenderService.process(context);
    }

    @Override
    public RenderFragmentType getType() {
        return RenderFragmentType.RENDER_GUI;
    }
}
