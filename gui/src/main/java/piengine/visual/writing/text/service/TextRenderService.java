package piengine.visual.writing.text.service;

import piengine.common.gui.writing.text.domain.Text;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.config.RenderFunction;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.writing.font.service.FontService;
import piengine.visual.writing.text.shader.TextShader;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextRenderService extends AbstractRenderService<TextShader> {

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
    protected void render(final RenderContext context) {
        shader.start();
        for (Text text : context.texts) {
            fontService.bind(text.font);
            shader.loadColor(text.color);
            shader.loadTranslation(text.translation);
            draw(text.dao);
        }
        shader.stop();
    }

    @Override
    public RenderType getType() {
        return RenderType.RENDER_TEXT;
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
