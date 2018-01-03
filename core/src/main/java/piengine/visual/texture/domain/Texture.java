package piengine.visual.texture.domain;

import piengine.core.base.domain.Domain;

public class Texture<T extends TextureDao> implements Domain<T> {

    private final T dao;

    public Texture(final T dao) {
        this.dao = dao;
    }

    @Override
    public T getDao() {
        return dao;
    }
}
