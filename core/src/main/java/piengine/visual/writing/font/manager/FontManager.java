package piengine.visual.writing.font.manager;

import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.service.FontService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class FontManager {

    private final FontService fontService;

    @Wire
    public FontManager(final FontService fontService) {
        this.fontService = fontService;
    }

    public Font supply(final String file) {
        return fontService.supply(file);
    }

}
