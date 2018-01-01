package piengine.object.model.service;

import piengine.object.model.domain.Model;
import piengine.object.model.shader.WorldShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.context.WorldRenderContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WorldRenderService extends AbstractRenderService<WorldShader, WorldRenderContext> {

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
    protected void render(final WorldRenderContext context) {
        renderInterpreter.setViewport(context.camera.viewport);

        shader.start()
                .loadLight(context.light)
                .loadProjectionMatrix(context.camera.projection)
                .loadViewMatrix(context.camera.view);

        for (Model model : context.models) {
            shader.loadModelMatrix(model.getModelMatrix())
                    .loadColor(model.attribute.color);

            if (model.attribute.texture != null) {
                shader.loadTextureEnabled(true);
                textureService.bind(model.attribute.texture);
            } else {
                shader.loadTextureEnabled(false);
            }

            draw(model.mesh.dao);
        }
        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .build();
    }
}
