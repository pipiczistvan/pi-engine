package piengine.visual.render.service;

import piengine.object.model.domain.Model;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.ModelShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class ModelRenderService extends AbstractRenderService<ModelShader, RenderWorldPlanContext> {

    private final TextureService textureService;

    @Wire
    public ModelRenderService(final ShaderService shaderService,
                              final TextureService textureService,
                              final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected ModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("modelShader")).castTo(ModelShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);
        renderInterpreter.setProvokingVertex(FIRST_VERTEX_CONVENTION);

        shader.start()
                .loadLights(context.lights)
                .loadFog(context.fog)
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.camera.getView())
                .loadClippingPlane(context.clippingPlane);

        for (Model model : context.models) {
            shader.loadModelMatrix(model.getModelMatrix())
                    .loadColor(model.color);

            if (model.texture != null) {
                shader.loadTextureEnabled(true);
                textureService.bind(model.texture);
            } else {
                shader.loadTextureEnabled(false);
            }

            draw(model.mesh.getDao());
        }
        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }
}
