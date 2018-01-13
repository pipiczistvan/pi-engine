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
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE3;
import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class WaterRenderService extends AbstractRenderService<WaterShader, RenderWorldPlanContext> {

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
                .loadFog(context.fog)
                .loadShadowMapSpaceMatrix(context.shadows.get(0).shadowMapSpaceMatrix)
                .loadTextureUnits();

        for (Water water : context.waters) {
            shader.loadModelMatrix(water.getModelMatrix())
                    .loadWaveFactor(water.waveFactor);

            textureService.bind(GL_TEXTURE0, water.reflectionBuffer);
            textureService.bind(GL_TEXTURE1, water.refractionBuffer);
            textureService.bind(GL_TEXTURE2,
                    water.refractionBuffer.getDao().attachments.get(FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT)
            );
            textureService.bind(GL_TEXTURE3, context.shadows.get(0).shadowMap);

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
