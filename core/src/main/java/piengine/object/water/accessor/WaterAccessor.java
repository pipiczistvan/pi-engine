package piengine.object.water.accessor;

import piengine.core.base.api.Accessor;
import piengine.object.water.domain.WaterData;
import piengine.object.water.domain.WaterKey;
import puppeteer.annotation.premade.Component;

@Component
public class WaterAccessor implements Accessor<WaterKey, WaterData> {

    private static final float[] vertices = {
            0, 0, 0,
            0, 0, 1,
            1, 0, 0,
            1, 0, 1
    };

    private static final int[] indices = {
            0, 1, 2,
            2, 1, 3
    };

    @Override
    public WaterData access(final WaterKey key) {
        return new WaterData(key.parent, vertices, indices);
    }
}
