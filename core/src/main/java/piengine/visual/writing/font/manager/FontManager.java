package piengine.visual.writing.font.manager;

import org.joml.Vector2i;
import piengine.visual.image.domain.ImageKey;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.domain.FontKey;
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

    public Font supply(final String file, final Vector2i resolution) {
        return fontService.supply(new FontKey(new ImageKey(file), resolution));
    }
}
