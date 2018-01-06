package piengine.object.water.domain;

import org.joml.Vector2i;
import piengine.object.entity.domain.Entity;

import java.util.Objects;

public class WaterKey {

    public final Entity parent;
    public final Vector2i size;

    public WaterKey(final Entity parent, final Vector2i size) {
        this.parent = parent;
        this.size = size;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterKey waterKey = (WaterKey) o;
        return Objects.equals(parent, waterKey.parent) &&
                Objects.equals(size, waterKey.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, size);
    }
}
