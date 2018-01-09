package piengine.visual.cubemap.domain;

import piengine.visual.texture.domain.Texture;

public class CubeMap extends Texture<CubeMapDao> {

    public CubeMap(final CubeMapDao dao) {
        super(dao);
    }
}
