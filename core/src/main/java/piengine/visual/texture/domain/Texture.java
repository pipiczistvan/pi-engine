package piengine.visual.texture.domain;

import piengine.core.base.domain.Domain;

public class Texture<T extends TextureDao> extends Domain<T> {

    public Texture(final T dao) {
        super(dao);
    }
}
