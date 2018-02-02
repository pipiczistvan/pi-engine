package piengine.object.water.domain;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.domain.Key;
import piengine.core.base.type.color.Color;

public class WaterKey implements Key {

    public final Vector2i resolution;
    public final Vector2i size;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;
    public final Color color;

    public WaterKey(final Vector2i resolution, final Vector2i size, final Vector3f position, final Vector3f rotation, final Vector3f scale, final Color color) {
        this.resolution = resolution;
        this.size = size;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.color = color;
    }
}
