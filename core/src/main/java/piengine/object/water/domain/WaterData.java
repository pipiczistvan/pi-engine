package piengine.object.water.domain;

import piengine.core.base.domain.ResourceData;
import piengine.object.entity.domain.Entity;

public class WaterData implements ResourceData {

    public final Entity parent;
    public final float[] vertices;
    public final float[] indicators;

    public WaterData(final Entity parent, final float[] vertices, final float[] indicators) {
        this.parent = parent;
        this.vertices = vertices;
        this.indicators = indicators;
    }
}
