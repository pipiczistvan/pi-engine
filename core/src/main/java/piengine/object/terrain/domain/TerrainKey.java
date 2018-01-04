package piengine.object.terrain.domain;

import piengine.object.entity.domain.Entity;

import java.util.Objects;

public class TerrainKey {

    public final Entity parent;
    public final String heightmap;

    public TerrainKey(final Entity parent, final String heightmap) {
        this.parent = parent;
        this.heightmap = heightmap;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TerrainKey that = (TerrainKey) o;
        return Objects.equals(parent, that.parent) &&
                Objects.equals(heightmap, that.heightmap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, heightmap);
    }
}
