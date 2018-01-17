package piengine.visual.cubemap.domain;

import piengine.core.base.domain.Key;
import piengine.visual.texture.domain.TextureData;

public class CubeMapKey implements Key {

    public final TextureData[] textureData;

    public CubeMapKey(final TextureData... textureData) {
        this.textureData = textureData;
    }
}
