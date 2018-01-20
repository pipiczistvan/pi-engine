package piengine.visual.lighting.point.shadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.ResourceData;

public class PointShadowData implements ResourceData {

    public final Vector2i resolution;

    public PointShadowData(final Vector2i resolution) {
        this.resolution = resolution;
    }
}
