package piengine.visual.writing.font.domain;

import piengine.visual.texture.domain.TextureDao;

public class FontDao extends TextureDao {

    private final int texture;

    public FontDao(final int texture) {
        this.texture = texture;
    }

    @Override
    public int getTexture() {
        return texture;
    }
}
