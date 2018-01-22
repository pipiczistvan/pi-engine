package piengine.object.animatedmodel.service;

import piengine.object.animatedmodel.shader.AnimatedModelShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatedModelRenderService extends AbstractRenderService<AnimatedModelShader, RenderWorldPlanContext> {

    @Wire
    public AnimatedModelRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected AnimatedModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("animatedModelShader")).castTo(AnimatedModelShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {

    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }
}
