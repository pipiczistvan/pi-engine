package piengine.visual.light;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.object.entity.domain.Entity;

public class Light extends Entity {

    public final Vector3f color;
    public final Vector2f bias;

    public Light(final Entity parent) {
        super(parent);

        this.color = new Vector3f(1);
        this.bias = new Vector2f(0.3f, 0.8f);
    }
}
