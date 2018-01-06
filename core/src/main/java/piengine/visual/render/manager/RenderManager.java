package piengine.visual.render.manager;

import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.service.RenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class RenderManager {

    private final RenderService renderService;

    @Wire
    public RenderManager(final RenderService renderService) {
        this.renderService = renderService;
    }

    public void render(final RenderPlan plan) {
        renderService.render(plan);
    }

    public void setWireFrameMode(final boolean wireFrameMode) {
        renderService.setWireFrameMode(wireFrameMode);
    }

}
