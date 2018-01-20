package piengine.visual.cubemap.domain;

import org.joml.Vector2i;
import piengine.visual.texture.domain.Texture;

public class CubeMap extends Texture<CubeMapDao> {

    public CubeMap(final CubeMapDao dao, final Vector2i size) {
        super(dao, size);
    }
}
