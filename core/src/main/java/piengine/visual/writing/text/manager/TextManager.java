package piengine.visual.writing.text.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.service.TextService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextManager extends SupplierManager<TextConfiguration, Text> {

    @Wire
    public TextManager(final TextService textService) {
        super(textService);
    }

    public void update(final Text text, final TextConfiguration config) {
        ((TextService) supplierService).update(text, config);
    }
}
