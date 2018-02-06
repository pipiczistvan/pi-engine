package piengine.visual.writing.font.manager;

import org.joml.Vector2i;
import piengine.core.architecture.manager.SupplierManager;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.domain.FontKey;
import piengine.visual.writing.font.service.FontService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class FontManager extends SupplierManager<FontKey, Font> {

    @Wire
    public FontManager(final FontService fontService) {
        super(fontService);
    }

    public Font supply(final String file, final Vector2i resolution) {
        return supply(new FontKey(file, resolution));
    }
}
