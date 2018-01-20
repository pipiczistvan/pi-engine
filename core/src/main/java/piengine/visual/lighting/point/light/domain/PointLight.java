package piengine.visual.lighting.point.light.domain;

import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.lighting.Light;
import piengine.visual.lighting.point.shadow.domain.PointShadow;

public class PointLight extends Light<PointShadow> {

    private final Vector3f attenuation;

    public PointLight(final Entity parent, final Color color, final Vector3f attenuation, final PointShadow shadow) {
        super(parent, color, shadow);
        this.attenuation = attenuation;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }
}
