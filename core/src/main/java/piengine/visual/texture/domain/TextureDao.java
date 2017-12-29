package piengine.visual.texture.domain;

import piengine.core.base.domain.Dao;

public class TextureDao implements Dao {

    public final int texture;

    public TextureDao(final int texture) {
        this.texture = texture;
    }

}
