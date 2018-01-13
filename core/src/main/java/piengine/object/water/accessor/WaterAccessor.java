package piengine.object.water.accessor;

import piengine.core.base.api.Accessor;
import piengine.object.water.domain.WaterData;
import piengine.object.water.domain.WaterGrid;
import piengine.object.water.domain.WaterKey;
import puppeteer.annotation.premade.Component;

@Component
public class WaterAccessor implements Accessor<WaterKey, WaterData> {

    private final WaterGridGenerator gridGenerator;

    public WaterAccessor() {
        this.gridGenerator = new WaterGridGenerator();
    }

    @Override
    public WaterData access(final WaterKey key) {
        WaterGrid grid = gridGenerator.generate(key.size);

        return new WaterData(grid.positions, grid.indicators, key.resolution);
    }
}
