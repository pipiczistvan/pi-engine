package piengine.visual.cubemap.domain;

import piengine.core.base.domain.Key;
import piengine.visual.image.domain.ImageKey;

import java.util.Arrays;

public class CubeMapKey implements Key {

    public final ImageKey[] images;

    public CubeMapKey(final ImageKey... images) {
        this.images = images;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubeMapKey that = (CubeMapKey) o;
        return Arrays.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(images);
    }
}
