package piengine.visual.render.service;

import piengine.object.water.domain.Water;
import piengine.visual.framebuffer.domain.FrameBufferAttachment;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.WaterShader;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class WaterRenderService extends AbstractRenderService<WaterShader, RenderWorldPlanContext> {

    private static final float WAVE_SPEED = 0.004f;

    private final TextureService textureService;
    private float time = 0;

    @Wire
    public WaterRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter,
                              final TextureService textureService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected WaterShader createShader(final ShaderService shaderService) {
        return shaderService.supply("waterShader").castTo(WaterShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        time += WAVE_SPEED; //todo: water attribútum legyen

        renderInterpreter.setViewport(context.camera.viewport);

        shader.start()
                .loadProjectionMatrix(context.camera.getProjection())
                .loadViewMatrix(context.camera.getView())
                .loadCameraPosition(context.camera.getPosition())
                .loadLight(context.light)
                .loadWaveTime(time)
                .loadTextureUnits();

        for (Water water : context.waters) {
            shader.loadModelMatrix(water.getModelMatrix());
            textureService.bind(GL_TEXTURE0, water.reflectionBuffer);
            textureService.bind(GL_TEXTURE1, water.refractionBuffer);
            textureService.bind(GL_TEXTURE2, water
                    .refractionBuffer
                    .getDao()
                    .attachments
                    .get(FrameBufferAttachment.DEPTH_TEXTURE_ATTACHMENT)
            );

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
