package piengine.visual.lighting.point.shadow.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.lighting.point.shadow.domain.PointShadowData;
import piengine.visual.lighting.point.shadow.domain.PointShadowKey;
import puppeteer.annotation.premade.Component;

@Component
public class PointShadowAccessor implements Accessor<PointShadowKey, PointShadowData> {

    @Override
    public PointShadowData access(final PointShadowKey key) {
        return new PointShadowData(key.resolution);
    }
}
