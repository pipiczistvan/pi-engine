package piengine.visual.skybox.manager;

import piengine.visual.skybox.domain.Skybox;
import piengine.visual.skybox.domain.SkyboxKey;
import piengine.visual.skybox.service.SkyboxService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class SkyboxManager {

    private final SkyboxService skyboxService;

    @Wire
    public SkyboxManager(final SkyboxService skyboxService) {
        this.skyboxService = skyboxService;
    }

    public Skybox supply(final SkyboxKey key) {
        return skyboxService.supply(key);
    }
}
