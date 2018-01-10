package piengine.visual.shadow.manager;

import piengine.visual.shadow.domain.Shadow;
import piengine.visual.shadow.domain.ShadowKey;
import piengine.visual.shadow.service.ShadowService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ShadowManager {

    private final ShadowService shadowService;

    @Wire
    public ShadowManager(final ShadowService shadowService) {
        this.shadowService = shadowService;
    }

    public Shadow supply(final ShadowKey key) {
        return shadowService.supply(key);
    }
}
