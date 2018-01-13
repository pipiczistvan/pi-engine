package piengine.object.water.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.EntityKey;
import piengine.object.entity.domain.Entity;

public class WaterKey extends EntityKey {

    public final Vector2i resolution;
    public final Vector2i size;

    public WaterKey(final Entity parent, final Vector2i resolution, final Vector2i size) {
        super(parent);
        this.resolution = resolution;
        this.size = size;
    }
}
