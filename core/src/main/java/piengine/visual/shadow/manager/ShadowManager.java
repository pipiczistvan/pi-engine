package piengine.visual.shadow.manager;

import org.joml.Vector2i;
import piengine.visual.camera.domain.Camera;
import piengine.visual.light.Light;
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

    public Shadow supply(final Light light, final Camera playerCamera, final Vector2i resolution) {
        return shadowService.supply(new ShadowKey(light, playerCamera, resolution));
    }
}
