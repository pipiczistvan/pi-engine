package piengine.object.water.manager;

import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.service.WaterService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WaterManager {

    private final WaterService waterService;

    @Wire
    public WaterManager(final WaterService waterService) {
        this.waterService = waterService;
    }

    public Water supply(WaterKey key) {
        return waterService.supply(key);
    }
}
