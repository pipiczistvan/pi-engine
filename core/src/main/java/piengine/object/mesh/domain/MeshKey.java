package piengine.object.mesh.domain;

import piengine.core.base.domain.Key;

import java.util.Objects;

public class MeshKey implements Key {

    public final String file;

    public MeshKey(final String file) {
        this.file = file;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeshKey meshKey = (MeshKey) o;
        return Objects.equals(file, meshKey.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
