package piengine.visual.writing.text.service;

import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.config.RenderFunction;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.shader.TextShader;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

@Component
public class TextRenderService extends AbstractRenderService<TextShader, RenderGuiPlanContext> {

    @Wire
    public TextRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected TextShader createShader() {
        return createShader("textShader", TextShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.start();
        for (Text text : context.texts) {
            shader.loadModelMatrix(text.getTransformation())
                    .loadColor(text.color);
            text.font.bind(GL_TEXTURE0);

            draw(text.vao);
        }
        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withDepthTest(false)
                .withBlendTest(true)
                .withWireFrameMode(false)
                .withRenderFunction(RenderFunction.DRAW_ARRAYS)
                .build();
    }
}
