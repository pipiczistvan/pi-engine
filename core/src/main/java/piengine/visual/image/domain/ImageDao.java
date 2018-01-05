package piengine.visual.image.domain;

import piengine.visual.texture.domain.TextureDao;

public class ImageDao extends TextureDao {

    private final int texture;

    public ImageDao(final int texture) {
        this.texture = texture;
    }

    @Override
    public int getTexture() {
        return texture;
    }
}
