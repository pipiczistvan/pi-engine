package piengine.visual.render.service;

import piengine.object.canvas.domain.Canvas;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.CanvasShader;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_NONE;

@Component
public class CanvasRenderService extends AbstractRenderService<CanvasShader, RenderGuiPlanContext> {

    private final TextureService textureService;

    @Wire
    public CanvasRenderService(final ShaderService shaderService,
                               final TextureService textureService,
                               final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected CanvasShader createShader(final ShaderService shaderService) {
        return shaderService.supply("canvasShader").castTo(CanvasShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.start();

        for (Canvas canvas : context.canvases) {
            shader.loadModelMatrix(canvas.getModelMatrix())
                    .loadColor(canvas.color);

            if (canvas.texture != null) {
                shader.loadTextureEnabled(true);
                textureService.bind(canvas.texture);
            } else {
                shader.loadTextureEnabled(false);
            }

            draw(canvas.mesh.getDao());
        }

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withDepthTest(false)
                .withCullFace(GL_NONE)
                .build();
    }
}
