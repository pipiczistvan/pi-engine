package piengine.visual.texture.domain;

import org.joml.Vector2i;
import piengine.core.base.domain.Domain;

public abstract class Texture<T extends TextureDao> implements Domain<T> {

    private final T dao;
    private final Vector2i size;

    public Texture(final T dao, final Vector2i size) {
        this.dao = dao;
        this.size = size;
    }

    @Override
    public T getDao() {
        return dao;
    }

    public Vector2i getSize() {
        return size;
    }
}
