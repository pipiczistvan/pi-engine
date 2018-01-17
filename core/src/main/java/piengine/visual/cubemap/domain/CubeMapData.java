package piengine.visual.cubemap.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.texture.domain.TextureData;

public class CubeMapData implements ResourceData {

    public final TextureData[] textureData;

    public CubeMapData(final TextureData[] textureData) {
        this.textureData = textureData;
    }
}
