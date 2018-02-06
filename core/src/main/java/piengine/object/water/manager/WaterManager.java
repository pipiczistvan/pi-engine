package piengine.object.water.manager;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.architecture.manager.SupplierManager;
import piengine.core.base.type.color.Color;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.service.WaterService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class WaterManager extends SupplierManager<WaterKey, Water> {

    @Wire
    public WaterManager(final WaterService waterService) {
        super(waterService);
    }

    public Water supply(final Vector2i resolution, final Vector2i size, final Vector3f position, final Vector3f rotation, final Vector3f scale, final Color color) {
        return supply(new WaterKey(resolution, size, position, rotation, scale, color));
    }

    public Water supply(final Vector2i resolution, final Vector2i size, final Vector3f position, final Vector3f scale, final Color color) {
        return supply(resolution, size, position, new Vector3f(0), scale, color);
    }
}
