package piengine.visual.render.service;

import piengine.core.base.exception.PIEngineException;
import piengine.object.canvas.domain.Canvas;
import piengine.visual.postprocessing.domain.PostProcessingEffectContext;
import piengine.visual.postprocessing.service.AbstractPostProcessingService;
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

import java.util.List;

import static org.lwjgl.opengl.GL11.GL_NONE;

@Component
public class CanvasRenderService extends AbstractRenderService<CanvasShader, RenderGuiPlanContext> {

    private final TextureService textureService;
    private final List<AbstractPostProcessingService> postProcessingServices;

    @Wire
    public CanvasRenderService(final ShaderService shaderService,
                               final TextureService textureService,
                               final RenderInterpreter renderInterpreter,
                               final List<AbstractPostProcessingService> postProcessingServices) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
        this.postProcessingServices = postProcessingServices;
    }

    @Override
    protected CanvasShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("canvasShader")).castTo(CanvasShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        for (Canvas canvas : context.canvases) {
            canvas.effectContexts.forEach(this::applyPostProcessing);

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

    private void applyPostProcessing(final PostProcessingEffectContext effectContext) {
        postProcessingServices.stream()
                .filter(pps -> pps.getEffectType().equals(effectContext.getEffectType()))
                .findFirst()
                .orElseThrow(() -> new PIEngineException("Could not find post processing service for type: %s!", effectContext.getEffectType()))
                .process(effectContext);
    }
}
