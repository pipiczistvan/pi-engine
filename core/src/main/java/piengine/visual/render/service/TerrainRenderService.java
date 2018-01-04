package piengine.visual.render.service;

import org.joml.Vector4f;
import piengine.object.terrain.domain.Terrain;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.context.TerrainRenderContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.TerrainShader;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TerrainRenderService extends AbstractRenderService<TerrainShader, TerrainRenderContext> {

    @Wire
    public TerrainRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected TerrainShader createShader(final ShaderService shaderService) {
        return shaderService.supply("terrainShader").castTo(TerrainShader.class);
    }

    @Override
    protected void render(final TerrainRenderContext context) {
        renderInterpreter.setViewport(context.camera.viewport);

        shader.start()
                .loadLight(context.light)
                .loadProjectionMatrix(context.camera.projection)
                .loadViewMatrix(context.camera.view);

        for (Terrain terrain : context.terrains) {
            shader.loadModelMatrix(terrain.getModelMatrix())
                    .loadColor(new Vector4f(1));

            draw(terrain.getDao());
        }

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .build();
    }
}
