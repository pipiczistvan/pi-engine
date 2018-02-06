package piengine.object.canvas.service;

import piengine.core.base.exception.PIEngineException;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.shader.CanvasShader;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.postprocessing.service.AbstractPostProcessingService;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.visual.render.domain.config.RenderFunction.DRAW_ARRAYS;

@Component
public class CanvasRenderService extends AbstractRenderService<CanvasShader, RenderGuiPlanContext> {

    private final List<AbstractPostProcessingService> postProcessingServices;

    @Wire
    public CanvasRenderService(final RenderInterpreter renderInterpreter,
                               final GlslLoader glslLoader,
                               final List<AbstractPostProcessingService> postProcessingServices) {
        super(renderInterpreter, glslLoader);
        this.postProcessingServices = postProcessingServices;
    }

    @Override
    protected CanvasShader createShader() {
        return createShader("canvasShader", CanvasShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        for (Canvas canvas : context.canvases) {
            Framebuffer currentFramebuffer = canvas.framebuffer;
            for (PostProcessingEffectContext effectContext : canvas.effectContexts) {
                currentFramebuffer = applyPostProcessing(currentFramebuffer, effectContext);
            }

            shader.start();
            shader.loadModelMatrix(canvas.getTransformation())
                    .loadColor(canvas.color);

            currentFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
            draw(canvas.vao);
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withRenderFunction(DRAW_ARRAYS)
                .withDrawMode(GL_TRIANGLE_STRIP)
                .withDepthTest(false)
                .withCullFace(GL_NONE)
                .withWireFrameMode(false)
                .build();
    }

    private Framebuffer applyPostProcessing(final Framebuffer framebuffer, final PostProcessingEffectContext effectContext) {
        return postProcessingServices.stream()
                .filter(pps -> pps.getEffectType().equals(effectContext.getEffectType()))
                .findFirst()
                .orElseThrow(() -> new PIEngineException("Could not find post processing service for type: %s!", effectContext.getEffectType()))
                .process(framebuffer, effectContext);
    }
}
