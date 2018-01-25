package piengine.object.animatedmodel.service;

import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.shader.AnimatedModelShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class AnimatedModelRenderService extends AbstractRenderService<AnimatedModelShader, RenderWorldPlanContext> {

    private final TextureService textureService;

    @Wire
    public AnimatedModelRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter,
                                      final TextureService textureService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected AnimatedModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("animatedModelShader")).castTo(AnimatedModelShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);
        renderInterpreter.setProvokingVertex(FIRST_VERTEX_CONVENTION);

        shader.loadDirectionalLights(context.directionalLights)
                .loadPointLights(context.pointLights)
                .loadFog(context.fog)
                .loadProjectionMatrix(context.currentCamera.getProjection())
                .loadViewMatrix(context.currentCamera.getView())
                .loadClippingPlane(context.clippingPlane);

        for (AnimatedModel animatedModel : context.animatedModels) {
            shader.loadJointTransforms(animatedModel.getJointTransforms())
                    .loadModelMatrix(animatedModel.getTransformation())
                    .loadLightEmitter(false);

            textureService.bind(animatedModel.texture);
            draw(animatedModel.getDao());
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }
}
