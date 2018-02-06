package piengine.object.model.domain;

import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.texture.Texture;

import java.util.Objects;

public class ModelKey {

    public final Entity parent;
    public final String objFile;
    public final Texture texture;
    public final Color color;
    public final boolean lightEmitter;

    public ModelKey(final Entity parent, final String objFile, final Texture texture, final Color color, final boolean lightEmitter) {
        this.parent = parent;
        this.objFile = objFile;
        this.texture = texture;
        this.color = color;
        this.lightEmitter = lightEmitter;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelKey modelKey = (ModelKey) o;
        return Objects.equals(objFile, modelKey.objFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objFile);
    }
}
