package piengine.visual.render.domain.fragment.handler;

import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import piengine.visual.writing.text.domain.TextRenderContext;
import piengine.visual.writing.text.service.TextRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.RENDER_TEXT;

@Component
public class RenderTextFragmentHandler extends FragmentHandler<TextRenderContext> {

    private final TextRenderService renderService;

    @Wire
    public RenderTextFragmentHandler(final TextRenderService renderService) {
        this.renderService = renderService;
    }

    @Override
    public void handle(final TextRenderContext context) {
        renderService.process(context);
    }

    @Override
    public void validate(final TextRenderContext context) {
        check("viewport", notNull(context.viewport));
        check("texts", notNull(context.texts));
    }

    @Override
    public RenderFragmentType getType() {
        return RENDER_TEXT;
    }
}
