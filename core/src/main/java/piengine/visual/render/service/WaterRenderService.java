package piengine.visual.render.service;

import piengine.object.water.domain.Water;
import piengine.visual.framebuffer.domain.FramebufferAttachment;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.WaterShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_DIRECTIONAL_LIGHT_COUNT;
import static piengine.core.base.type.property.PropertyKeys.LIGHTING_POINT_LIGHT_COUNT;
import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class WaterRenderService extends AbstractRenderService<WaterShader, RenderWorldPlanContext> {

    private static final int DIRECTIONAL_LIGHT_COUNT = get(LIGHTING_DIRECTIONAL_LIGHT_COUNT);
    private static final int POINT_LIGHT_COUNT = get(LIGHTING_POINT_LIGHT_COUNT);

    private final TextureService textureService;

    @Wire
    public WaterRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter,
                              final TextureService textureService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected WaterShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("waterShader")).castTo(WaterShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.start()
                .loadProjectionMatrix(context.currentCamera.getProjection())
                .loadViewMatrix(context.currentCamera.getView())
                .loadCameraPosition(context.currentCamera.getPosition())
                .loadDirectionalLights(context.directionalLights)
                .loadPointLights(context.pointLights)
                .loadFog(context.fog)
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

        for (Water water : context.waters) {
            shader.loadWaveFactor(water.waveFactor);
            textureService.bind(GL_TEXTURE0 + textureIndex++, water.reflectionBuffer);
            textureService.bind(GL_TEXTURE0 + textureIndex++, water.refractionBuffer);
            textureService.bind(GL_TEXTURE0 + textureIndex++, water.refractionBuffer.getDao().getAttachment(FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT));

            draw(water.getDao());
        }

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withRenderFunction(DRAW_ARRAYS)
                .withBlendTest(true)
                .build();
    }
}
