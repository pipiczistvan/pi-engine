package piengine.visual.cubemap.domain;

import piengine.core.base.domain.Key;
import piengine.visual.texture.domain.TextureData;

public class CubeMapKey implements Key {

    public final int format;
    public final int type;
    public final TextureData[] textureData;

    public CubeMapKey(final int format, final int type, final TextureData... textureData) {
        this.format = format;
        this.type = type;
        this.textureData = textureData;
    }
}
