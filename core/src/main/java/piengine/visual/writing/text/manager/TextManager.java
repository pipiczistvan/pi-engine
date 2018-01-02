package piengine.visual.writing.text.manager;

import piengine.object.entity.domain.Entity;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.service.TextService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextManager {

    private final TextService textService;

    @Wire
    public TextManager(final TextService textService) {
        this.textService = textService;
    }

    public Text supply(final TextConfiguration config, final Entity parent) {
        return textService.supply(config, parent);
    }

    public void update(final Text text, final TextConfiguration config) {
        textService.update(text, config);
    }
}
