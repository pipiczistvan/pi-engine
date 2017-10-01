package piengine.visual.light;

import org.joml.Vector3f;
import piengine.object.entity.domain.Entity;

public class Light extends Entity {

    public final Vector3f color;

    public Light(final Entity parent) {
        super(parent);

        this.color = new Vector3f(1);
    }

}
