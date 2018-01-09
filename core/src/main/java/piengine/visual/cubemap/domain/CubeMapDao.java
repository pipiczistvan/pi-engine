package piengine.visual.cubemap.domain;

import piengine.visual.texture.domain.TextureDao;

public class CubeMapDao extends TextureDao {

    private final int texture;

    public CubeMapDao(final int texture) {
        this.texture = texture;
    }

    @Override
    public int getTexture() {
        return texture;
    }
}
