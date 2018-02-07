package piengine.visual.lighting.point.shadow.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.lighting.point.shadow.domain.PointShadowData;
import piengine.visual.lighting.point.shadow.domain.PointShadowKey;
import puppeteer.annotation.premade.Component;

@Component
public class PointShadowAccessor extends Accessor<PointShadowKey, PointShadowData> {

    @Override
    protected PointShadowData accessResource(final PointShadowKey key) {
        return new PointShadowData(key.resolution);
    }

    @Override
    protected String getAccessInfo(final PointShadowKey key, final PointShadowData resource) {
        return String.format("Resolution: %s", key.resolution);
    }
}
