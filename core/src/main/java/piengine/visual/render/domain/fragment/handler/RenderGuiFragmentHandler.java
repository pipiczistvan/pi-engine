package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.service.GuiRenderService;
import piengine.visual.writing.text.service.TextRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class RenderGuiFragmentHandler implements FragmentHandler<RenderGuiPlanContext> {

    private final GuiRenderService guiRenderService;
    private final TextRenderService textRenderService;

    @Wire
    public RenderGuiFragmentHandler(final GuiRenderService guiRenderService, final TextRenderService textRenderService) {
        this.guiRenderService = guiRenderService;
        this.textRenderService = textRenderService;
    }

    @Override
    public void handle(final RenderGuiPlanContext context) {
        guiRenderService.process(context);
        textRenderService.process(context);
    }

    @Override
    public RenderFragmentType getType() {
        return RenderFragmentType.RENDER_GUI;
    }
}
