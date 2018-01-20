package piengine.visual.render.service;

import piengine.object.terrain.domain.Terrain;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.TerrainShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_DIRECTIONAL_LIGHT_COUNT;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_POINT_LIGHT_COUNT;
import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class TerrainRenderService extends AbstractRenderService<TerrainShader, RenderWorldPlanContext> {

    private static final int DIRECTIONAL_LIGHT_COUNT = get(LIGHTING_DIRECTIONAL_LIGHT_COUNT);
    private static final int POINT_LIGHT_COUNT = get(LIGHTING_POINT_LIGHT_COUNT);

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
                .loadDirectionalLights(context.directionalLights)
                .loadPointLights(context.pointLights)
                .loadFog(context.fog)
                .loadProjectionMatrix(context.currentCamera.getProjection())
                .loadViewMatrix(context.currentCamera.getView())
                .loadCameraPosition(context.currentCamera.getPosition())
                .loadClippingPlane(context.clippingPlane)
                .loadTextureUnits();

        int textureIndex = 0;
        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            if (i < context.directionalLights.size() && context.directionalLights.get(i).getShadow() != null) {
                textureService.bind(GL_TEXTURE0 + textureIndex++, context.directionalLights.get(i).getShadow().getShadowMap());
            } else {
                textureService.bind(GL_TEXTURE0 + textureIndex++, -1);
            }
        }
        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            if (i < context.pointLights.size() && context.pointLights.get(i).getShadow() != null) {
                textureService.bindCubeMap(GL_TEXTURE0 + textureIndex++, context.pointLights.get(i).getShadow().getShadowMap());
            } else {
                textureService.bindCubeMap(GL_TEXTURE0 + textureIndex++, -1);
            }
        }

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
