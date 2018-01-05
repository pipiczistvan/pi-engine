package piengine.object.water.domain;

import piengine.object.entity.domain.Entity;

import java.util.Objects;

public class WaterKey {

    public final Entity parent;

    public WaterKey(final Entity parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterKey waterKey = (WaterKey) o;
        return Objects.equals(parent, waterKey.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent);
    }
}
