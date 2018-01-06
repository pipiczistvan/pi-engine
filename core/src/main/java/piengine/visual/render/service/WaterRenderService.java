package piengine.visual.render.service;

import piengine.object.water.domain.Water;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.WaterShader;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WaterRenderService extends AbstractRenderService<WaterShader, RenderWorldPlanContext> {

    @Wire
    public WaterRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected WaterShader createShader(final ShaderService shaderService) {
        return shaderService.supply("waterShader").castTo(WaterShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.camera.viewport);

        shader.start()
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.camera.getView());

        for (Water water : context.waters) {
            shader.loadModelMatrix(water.getModelMatrix());

            draw(water.getDao());
        }

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .build();
    }
}
