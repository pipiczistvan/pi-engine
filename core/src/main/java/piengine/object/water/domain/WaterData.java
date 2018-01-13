package piengine.object.water.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;

public class WaterData implements ResourceData {

    public final float[] vertices;
    public final float[] indicators;
    public final Vector2i resolution;

    public WaterData(final float[] vertices, final float[] indicators, final Vector2i resolution) {
        this.vertices = vertices;
        this.indicators = indicators;
        this.resolution = resolution;
    }
}
