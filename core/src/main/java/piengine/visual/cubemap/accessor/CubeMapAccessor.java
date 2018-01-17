package piengine.visual.cubemap.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.cubemap.domain.CubeMapData;
import piengine.visual.cubemap.domain.CubeMapKey;
import puppeteer.annotation.premade.Component;

@Component
public class CubeMapAccessor implements Accessor<CubeMapKey, CubeMapData> {

    @Override
    public CubeMapData access(final CubeMapKey key) {
        return new CubeMapData(key.textureData);
    }
}
