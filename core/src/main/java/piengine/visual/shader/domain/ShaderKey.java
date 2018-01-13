package piengine.visual.shader.domain;

import piengine.core.base.domain.Key;

import java.util.Objects;

public class ShaderKey implements Key {

    public final String file;

    public ShaderKey(final String file) {
        this.file = file;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShaderKey shaderKey = (ShaderKey) o;
        return Objects.equals(file, shaderKey.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
