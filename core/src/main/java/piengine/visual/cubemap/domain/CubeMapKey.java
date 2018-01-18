package piengine.visual.cubemap.domain;

import piengine.core.base.domain.Key;
import piengine.visual.texture.domain.TextureData;

public class CubeMapKey implements Key {

    public final int format;
    public final TextureData[] textureData;

    public CubeMapKey(final int format, final TextureData... textureData) {
        this.format = format;
        this.textureData = textureData;
    }
}
