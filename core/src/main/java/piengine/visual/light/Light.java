package piengine.visual.light;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.object.entity.domain.Entity;

public class Light extends Entity {

    private final Color color;
    private final Vector2f bias;
    private final Vector3f attenuation;

    public Light(final Entity parent) {
        super(parent);
        this.color = new Color(ColorUtils.BLACK);
        this.bias = new Vector2f(0.3f, 0.8f);
        this.attenuation = new Vector3f(1, 0, 0);
    }

    public Color getColor() {
        return color;
    }

    public Vector2f getBias() {
        return bias;
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
