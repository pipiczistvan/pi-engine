package piengine.visual.light;

import org.joml.Vector2f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.object.entity.domain.Entity;

public class Light extends Entity {

    public final Color color;
    public final Vector2f bias;

    public Light(final Entity parent) {
        super(parent);

        this.color = new Color(ColorUtils.WHITE);
        this.bias = new Vector2f(0.3f, 0.8f);
    }
}
