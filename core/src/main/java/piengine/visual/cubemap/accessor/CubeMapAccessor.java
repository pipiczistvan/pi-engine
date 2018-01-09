package piengine.visual.cubemap.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.cubemap.domain.CubeMapData;
import piengine.visual.cubemap.domain.CubeMapKey;
import piengine.visual.image.accessor.ImageAccessor;
import piengine.visual.image.domain.ImageData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CubeMapAccessor implements Accessor<CubeMapKey, CubeMapData> {

    private final ImageAccessor imageAccessor;

    @Wire
    public CubeMapAccessor(final ImageAccessor imageAccessor) {
        this.imageAccessor = imageAccessor;
    }

    @Override
    public CubeMapData access(final CubeMapKey key) {
        List<ImageData> imageData = Arrays.stream(key.textures).map(imageAccessor::access).collect(Collectors.toList());

        return new CubeMapData(imageData);
    }

    @Override
    public void free(final CubeMapData resource) {
        resource.imageData.forEach(imageAccessor::free);
    }
}
