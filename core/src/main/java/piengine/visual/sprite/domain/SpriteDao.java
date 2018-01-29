package piengine.visual.sprite.domain;

import piengine.visual.texture.domain.TextureDao;

public class SpriteDao extends TextureDao {

    private final int texture;

    public SpriteDao(final int texture) {
        this.texture = texture;
    }

    @Override
    public int getTexture() {
        return texture;
    }
}
