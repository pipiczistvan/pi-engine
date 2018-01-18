package piengine.visual.render.service;

import piengine.object.terrain.domain.Terrain;
import piengine.visual.pointshadow.domain.PointShadow;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.TerrainShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE16;
import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class TerrainRenderService extends AbstractRenderService<TerrainShader, RenderWorldPlanContext> {

    private final TextureService textureService;

    @Wire
    public TerrainRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter,
                                final TextureService textureService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected TerrainShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("terrainShader")).castTo(TerrainShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);
        renderInterpreter.setProvokingVertex(FIRST_VERTEX_CONVENTION);

        shader.start()
                .loadLights(context.lights)
                .loadShadows(context.shadows)
                .loadFog(context.fog)
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.camera.getView())
                .loadClippingPlane(context.clippingPlane)
                .loadTextureUnits(context.shadows);

        int textureIndex = 0;
        for (Shadow shadow : context.shadows) {
            textureService.bind(GL_TEXTURE0 + textureIndex++, shadow.shadowMap);
        }

        PointShadow pointShadow = context.pointShadows.get(0);//todo: only 1
        textureService.bindCubeMap(GL_TEXTURE16, pointShadow.getShadowMap());

        shader.loadPointShadowPosition(pointShadow.getLight().getPosition());

        for (Terrain terrain : context.terrains) {
            draw(terrain.getDao());
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
