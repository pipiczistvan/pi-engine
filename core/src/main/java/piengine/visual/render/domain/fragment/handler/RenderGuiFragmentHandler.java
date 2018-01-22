package piengine.visual.render.domain.fragment.handler;

import piengine.object.canvas.service.CanvasRenderService;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.writing.text.service.TextRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class RenderGuiFragmentHandler implements FragmentHandler<RenderGuiPlanContext> {

    private final CanvasRenderService canvasRenderService;
    private final TextRenderService textRenderService;

    @Wire
    public RenderGuiFragmentHandler(final CanvasRenderService canvasRenderService, final TextRenderService textRenderService) {
        this.canvasRenderService = canvasRenderService;
        this.textRenderService = textRenderService;
    }

    @Override
    public void handle(final RenderGuiPlanContext context) {
        canvasRenderService.process(context);
        textRenderService.process(context);
    }

    @Override
    public RenderFragmentType getType() {
        return RenderFragmentType.RENDER_GUI;
    }
}
