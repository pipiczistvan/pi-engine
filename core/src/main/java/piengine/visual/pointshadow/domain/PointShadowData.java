package piengine.visual.pointshadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;
import piengine.visual.light.domain.Light;

public class PointShadowData implements ResourceData {

    public final Light light;
    public final Vector2i resolution;

    public PointShadowData(final Light light, final Vector2i resolution) {
        this.light = light;
        this.resolution = resolution;
    }
}
