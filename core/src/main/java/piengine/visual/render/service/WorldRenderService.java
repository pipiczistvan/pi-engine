package piengine.visual.render.service;

import piengine.object.model.domain.Model;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.WorldShader;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class WorldRenderService extends AbstractRenderService<WorldShader, RenderWorldPlanContext> {

    private final TextureService textureService;

    @Wire
    public WorldRenderService(final ShaderService shaderService,
                              final TextureService textureService,
                              final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected WorldShader createShader(final ShaderService shaderService) {
        return shaderService.supply("worldShader").castTo(WorldShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);
        renderInterpreter.setProvokingVertex(FIRST_VERTEX_CONVENTION);

        shader.start()
                .loadLight(context.light)
                .loadFog(context.fog)
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.camera.getView())
                .loadClippingPlane(context.clippingPlane);

        for (Model model : context.models) {
            shader.loadModelMatrix(model.getModelMatrix())
                    .loadColor(model.attribute.color);

            if (model.attribute.texture != null) {
                shader.loadTextureEnabled(true);
                textureService.bind(model.attribute.texture);
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
