package piengine.visual.cubemap.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.texture.domain.TextureData;

public class CubeMapData implements ResourceData {

    public final int format;
    public final TextureData[] textureData;

    public CubeMapData(final int format, final TextureData[] textureData) {
        this.format = format;
        this.textureData = textureData;
    }
}
