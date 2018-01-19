package piengine.visual.render.service;

import piengine.core.base.type.property.PropertyKeys;
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
import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class WaterRenderService extends AbstractRenderService<WaterShader, RenderWorldPlanContext> {

    private static final int LIGHT_COUNT = get(PropertyKeys.LIGHT_COUNT);

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
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.camera.getView())
                .loadCameraPosition(context.camera.getPosition())
                .loadLights(context.lights)
                .loadShadows(context.shadows)
                .loadPointShadows(context.pointShadows)
                .loadFog(context.fog)
                .loadTextureUnits();


        int textureIndex = 0;
        for (int i = 0; i < LIGHT_COUNT; i++) {
            if (i < context.shadows.size()) {
                textureService.bind(GL_TEXTURE0 + textureIndex++, context.shadows.get(i).shadowMap);
            } else {
                textureService.bind(GL_TEXTURE0 + textureIndex++, -1);
            }
        }

        for (int i = 0; i < LIGHT_COUNT; i++) {
            if (i < context.pointShadows.size()) {
                textureService.bindCubeMap(GL_TEXTURE0 + textureIndex++, context.pointShadows.get(i).getShadowMap());
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
