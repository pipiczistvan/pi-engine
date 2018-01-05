package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.context.WaterRenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.render.service.WaterRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_WATER;

@Component
public class RenderWaterFragmentHandler extends FragmentHandler<WaterRenderContext> {

    private final WaterRenderService renderService;

    @Wire
    public RenderWaterFragmentHandler(final WaterRenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void handle(final WaterRenderContext context) {
        renderService.process(context);
    }

    @Override
    public void validate(final WaterRenderContext context) {
        check("camera", notNull(context.camera));
        check("water", notNull(context.water));
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_WATER;
    }
}
