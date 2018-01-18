package piengine.visual.pointshadow.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;
import piengine.visual.light.domain.Light;

public class PointShadowKey implements Key {

    public final Light light;
    public final Vector2i resolution;

    public PointShadowKey(final Light light, final Vector2i resolution) {
        this.light = light;
        this.resolution = resolution;
    }
}
