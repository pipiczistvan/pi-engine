package piengine.object.animatedmodel.service;

import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.shader.AnimatedModelShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class AnimatedModelRenderService extends AbstractRenderService<AnimatedModelShader, RenderWorldPlanContext> {

    @Wire
    public AnimatedModelRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected AnimatedModelShader createShader() {
        return createShader("animatedModelShader", AnimatedModelShader.class);
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

            animatedModel.texture.bind(GL_TEXTURE0);
            draw(animatedModel.vao);
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }
}
