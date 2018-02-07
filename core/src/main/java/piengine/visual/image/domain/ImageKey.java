package piengine.visual.image.domain;

import piengine.core.base.domain.Key;

import java.util.Objects;

public class ImageKey implements Key {

    public final String file;

    public ImageKey(final String file) {
        this.file = file;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageKey imageKey = (ImageKey) o;
        return Objects.equals(file, imageKey.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
