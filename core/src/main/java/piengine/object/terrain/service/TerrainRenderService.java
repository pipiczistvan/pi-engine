package piengine.object.terrain.service;

import piengine.io.interpreter.cubemap.CubeMap;
import piengine.io.interpreter.texture.Texture;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.shader.TerrainShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_DIRECTIONAL_LIGHT_COUNT;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_POINT_LIGHT_COUNT;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.DEPTH;
import static piengine.visual.render.domain.config.ProvokingVertex.FIRST_VERTEX_CONVENTION;

@Component
public class TerrainRenderService extends AbstractRenderService<TerrainShader, RenderWorldPlanContext> {

    private static final int DIRECTIONAL_LIGHT_COUNT = get(LIGHTING_DIRECTIONAL_LIGHT_COUNT);
    private static final int POINT_LIGHT_COUNT = get(LIGHTING_POINT_LIGHT_COUNT);

    @Wire
    public TerrainRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected TerrainShader createShader() {
        return createShader("terrainShader", TerrainShader.class);
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
                .loadCameraPosition(context.currentCamera.getPosition())
                .loadClippingPlane(context.clippingPlane)
                .loadTextureUnits();

        int textureIndex = 0;
        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            if (i < context.directionalLights.size() && context.directionalLights.get(i).getShadow() != null) {
                context.directionalLights.get(i).getShadow().getShadowMap().getTextureAttachment(DEPTH).bind(GL_TEXTURE0 + textureIndex++);
            } else {
                Texture.bindMinus(GL_TEXTURE0 + textureIndex++);
            }
        }
        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            if (i < context.pointLights.size() && context.pointLights.get(i).getShadow() != null) {
                context.pointLights.get(i).getShadow().getShadowMap().getCubeMapAttachment(DEPTH).bind(GL_TEXTURE0 + textureIndex++);
            } else {
                CubeMap.bindMinus(GL_TEXTURE0 + textureIndex++);
            }
        }

        for (Terrain terrain : context.terrains) {
            draw(terrain.vao);
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }
}
