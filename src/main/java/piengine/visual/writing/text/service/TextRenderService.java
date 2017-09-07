package piengine.visual.writing.text.service;

import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.RenderType;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.writing.font.service.FontService;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.shader.TextShader;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.RenderType.RENDER_TEXT;

@Component
public class TextRenderService extends AbstractRenderService<TextShader> {

    private final FontService fontService;
    private final RenderInterpreter renderInterpreter;

    @Wire
    public TextRenderService(final ShaderService shaderService,
                             final FontService fontService,
                             final RenderInterpreter renderInterpreter) {
        super(shaderService);

        this.fontService = fontService;
        this.renderInterpreter = renderInterpreter;
    }

    @Override
    protected TextShader createShader(final ShaderService shaderService) {
        return shaderService.supply("textShader").castTo(TextShader.class);
    }

    @Override
    public void render(final RenderContext context) {
        renderInterpreter.setBlendTest(true);
        renderInterpreter.setDepthTest(false);
        shader.start();

        for (Text text : context.texts) {
            fontService.bind(text.font);
            shader.loadColor(text.color);
            shader.loadTranslation(text.translation);
            renderInterpreter.renderText(text.dao);
        }

        shader.stop();
        renderInterpreter.setBlendTest(false);
        renderInterpreter.setDepthTest(true);
    }

    @Override
    public RenderType getType() {
        return RENDER_TEXT;
    }
}
