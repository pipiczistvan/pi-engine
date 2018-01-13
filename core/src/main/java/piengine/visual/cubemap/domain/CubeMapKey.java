package piengine.visual.cubemap.domain;

import piengine.core.base.domain.Key;

import java.util.Arrays;

public class CubeMapKey implements Key {

    public final String[] textures;

    public CubeMapKey(final String[] textures) {
        this.textures = textures;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubeMapKey that = (CubeMapKey) o;
        return Arrays.equals(textures, that.textures);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(textures);
    }
}
