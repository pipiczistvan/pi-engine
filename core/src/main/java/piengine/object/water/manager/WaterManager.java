package piengine.object.water.manager;

import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;
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

    public Water supply(final Entity parent, final Vector2i resolution, final Vector2i size) {
        return waterService.supply(new WaterKey(parent, resolution, size));
    }
}
