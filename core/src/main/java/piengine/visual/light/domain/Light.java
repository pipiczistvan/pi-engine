package piengine.visual.light.domain;

import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;

import static piengine.core.utils.ColorUtils.BLACK;

public class Light extends Entity {

    private final Color color;
    private final Vector3f attenuation;

    public Light(final Entity parent) {
        super(parent);
        this.color = new Color(BLACK);
        this.attenuation = new Vector3f(1, 0, 0);
    }

    public Color getColor() {
        return color;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setColor(final float r, final float g, final float b) {
        color.set(r, g, b);
    }

    public void setAttenuation(final float x, final float y, final float z) {
        attenuation.set(x, y, z);
    }
}
