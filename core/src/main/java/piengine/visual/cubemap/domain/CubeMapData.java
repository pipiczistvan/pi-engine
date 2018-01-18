package piengine.visual.cubemap.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.texture.domain.TextureData;

public class CubeMapData implements ResourceData {

    public final int format;
    public final int type;
    public final TextureData[] textureData;

    public CubeMapData(final int format, final int type, final TextureData[] textureData) {
        this.format = format;
        this.type = type;
        this.textureData = textureData;
    }
}
