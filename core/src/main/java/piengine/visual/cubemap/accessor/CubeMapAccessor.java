package piengine.visual.cubemap.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.cubemap.domain.CubeMapData;
import piengine.visual.cubemap.domain.CubeMapKey;
import puppeteer.annotation.premade.Component;

@Component
public class CubeMapAccessor extends Accessor<CubeMapKey, CubeMapData> {

    @Override
    protected CubeMapData accessResource(final CubeMapKey key) {
        return new CubeMapData(key.format, key.type, key.textureData);
    }

    @Override
    protected String getAccessInfo(final CubeMapKey key, final CubeMapData resource) {
        return "";
    }
}
