package piengine.object.model.service;

import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.model.domain.Model;
import piengine.object.model.shader.ModelShader;
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
public class ModelRenderService extends AbstractRenderService<ModelShader, RenderWorldPlanContext> {

    @Wire
    public ModelRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected ModelShader createShader() {
        return createShader("modelShader", ModelShader.class);
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

        for (Model model : context.models) {
            shader.loadModelMatrix(model.getTransformation())
                    .loadLightEmitter(model.lightEmitter)
                    .loadColor(model.color);

            if (model.texture != null) {
                shader.loadTextureEnabled(true);
                model.texture.bind(GL_TEXTURE0);
            } else {
                shader.loadTextureEnabled(false);
            }

            draw(model.vao);
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }
}
