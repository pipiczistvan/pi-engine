package piengine.visual.cubemap.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.image.domain.ImageData;

import java.util.List;

public class CubeMapData implements ResourceData {

    public final List<ImageData> imageData;

    public CubeMapData(final List<ImageData> imageData) {
        this.imageData = imageData;
    }
}
