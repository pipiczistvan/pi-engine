package piengine.object.water.domain;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.domain.ResourceData;

public class WaterData implements ResourceData {

    public final float[] vertices;
    public final float[] indicators;
    public final Vector2i resolution;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;

    public WaterData(final float[] vertices, final float[] indicators, final Vector2i resolution,
                     final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        this.vertices = vertices;
        this.indicators = indicators;
        this.resolution = resolution;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
}
