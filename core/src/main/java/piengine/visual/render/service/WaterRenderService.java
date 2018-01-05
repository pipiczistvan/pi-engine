package piengine.visual.render.service;

import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.context.WaterRenderContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.WaterShader;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WaterRenderService extends AbstractRenderService<WaterShader, WaterRenderContext> {

    @Wire
    public WaterRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected WaterShader createShader(final ShaderService shaderService) {
        return shaderService.supply("waterShader").castTo(WaterShader.class);
    }

    @Override
    protected void render(final WaterRenderContext context) {
        renderInterpreter.setViewport(context.camera.viewport);

        shader.start()
                .loadProjectionMatrix(context.camera.projection)
                .loadViewMatrix(context.camera.view)
                .loadModelMatrix(context.water.getModelMatrix());

        draw(context.water.getDao());

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .build();
    }
}
