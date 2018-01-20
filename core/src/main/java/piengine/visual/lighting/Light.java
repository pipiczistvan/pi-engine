package piengine.visual.lighting;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;

public abstract class Light<S extends Shadow> extends Entity {

    private final Color color;
    private final S shadow;

    public Light(final Entity parent, final Color color, final S shadow) {
        super(parent);
        this.color = color;
        this.shadow = shadow;
    }

    public Color getColor() {
        return color;
    }

    public S getShadow() {
        return shadow;
    }
}
