package piengine.visual.render.service;

import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.SkyboxShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class SkyboxRenderService extends AbstractRenderService<SkyboxShader, RenderWorldPlanContext> {

    private final TextureService textureService;

    @Wire
    public SkyboxRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter,
                               final TextureService textureService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected SkyboxShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("skyboxShader")).castTo(SkyboxShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.start()
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.skybox.getView(context.camera))
                .loadFog(context.fog);

        textureService.bindCubeMap(context.skybox.cubeMap);
        draw(context.skybox.getDao());

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withRenderFunction(DRAW_ARRAYS)
                .build();
    }
}
