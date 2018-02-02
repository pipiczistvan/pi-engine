package piengine.visual.writing.font.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Key;
import piengine.visual.image.domain.ImageKey;

import java.util.Objects;

public class FontKey implements Key {

    public final ImageKey imageKey;
    public final Vector2i resolution;

    public FontKey(final ImageKey imageKey, final Vector2i resolution) {
        this.imageKey = imageKey;
        this.resolution = resolution;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontKey fontKey = (FontKey) o;
        return Objects.equals(imageKey, fontKey.imageKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageKey);
    }
}
