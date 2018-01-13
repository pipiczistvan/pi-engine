package piengine.visual.writing.font.domain;

import piengine.core.base.domain.Key;

import java.util.Objects;

public class FontKey implements Key {

    public final String file;

    public FontKey(final String file) {
        this.file = file;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontKey fontKey = (FontKey) o;
        return Objects.equals(file, fontKey.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
