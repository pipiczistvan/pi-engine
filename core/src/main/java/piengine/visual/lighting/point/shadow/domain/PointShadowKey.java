package piengine.visual.lighting.point.shadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;

public class PointShadowKey implements Key {

    public final Vector2i resolution;

    public PointShadowKey(final Vector2i resolution) {
        this.resolution = resolution;
    }
}
