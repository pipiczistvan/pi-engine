package piengine.visual.render.service;

import piengine.object.canvas.domain.Canvas;
import piengine.visual.postprocessing.service.PostProcessingService;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.CanvasShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_NONE;

@Component
public class CanvasRenderService extends AbstractRenderService<CanvasShader, RenderGuiPlanContext> {

    private final TextureService textureService;
    private final PostProcessingService postProcessingService;

    @Wire
    public CanvasRenderService(final ShaderService shaderService,
                               final TextureService textureService,
                               final RenderInterpreter renderInterpreter,
                               final PostProcessingService postProcessingService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
        this.postProcessingService = postProcessingService;
    }

    @Override
    protected CanvasShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("canvasShader")).castTo(CanvasShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        for (Canvas canvas : context.canvases) {
            canvas.effects.forEach(postProcessingService::process);

            shader.start();
            shader.loadModelMatrix(canvas.getTransformation())
                    .loadColor(canvas.color);

            textureService.bind(canvas.texture);
            draw(canvas.mesh.getDao());
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withDepthTest(false)
                .withCullFace(GL_NONE)
                .withWireFrameMode(false)
                .build();
    }
}
