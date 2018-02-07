package piengine.object.water.accessor;

import piengine.core.base.api.Accessor;
import piengine.object.water.domain.WaterData;
import piengine.object.water.domain.WaterGrid;
import piengine.object.water.domain.WaterKey;
import puppeteer.annotation.premade.Component;

@Component
public class WaterAccessor extends Accessor<WaterKey, WaterData> {

    private final WaterGridGenerator gridGenerator;

    public WaterAccessor() {
        this.gridGenerator = new WaterGridGenerator();
    }

    @Override
    protected WaterData accessResource(final WaterKey key) {
        WaterGrid grid = gridGenerator.generate(key.size, key.position, key.rotation, key.scale);

        return new WaterData(grid.positions, grid.indicators, key.resolution, key.position, key.rotation, key.scale, key.color);
    }

    @Override
    protected String getAccessInfo(final WaterKey key, final WaterData resource) {
        return String.format("Resolution: ", key.resolution);
    }
}
