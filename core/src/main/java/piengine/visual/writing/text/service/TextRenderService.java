package piengine.visual.writing.text.service;

import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.config.RenderFunction;
import piengine.visual.render.domain.fragment.domain.RenderGuiPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.writing.font.service.FontService;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.shader.TextShader;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextRenderService extends AbstractRenderService<TextShader, RenderGuiPlanContext> {

    private final FontService fontService;

    @Wire
    public TextRenderService(final ShaderService shaderService,
                             final FontService fontService,
                             final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);

        this.fontService = fontService;
    }

    @Override
    protected TextShader createShader(final ShaderService shaderService) {
        return shaderService.supply("textShader").castTo(TextShader.class);
    }

    @Override
    protected void render(final RenderGuiPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        shader.start();
        for (Text text : context.texts) {
            shader.loadModelMatrix(text.getModelMatrix())
                    .loadColor(text.color);
            fontService.bind(text.font);

            draw(text.getDao());
        }
        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withDepthTest(false)
                .withBlendTest(true)
                .withRenderFunction(RenderFunction.DRAW_ARRAYS)
                .build();
    }
}
