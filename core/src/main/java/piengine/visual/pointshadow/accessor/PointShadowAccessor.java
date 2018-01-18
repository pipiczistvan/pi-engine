package piengine.visual.pointshadow.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.pointshadow.domain.PointShadowData;
import piengine.visual.pointshadow.domain.PointShadowKey;
import puppeteer.annotation.premade.Component;

@Component
public class PointShadowAccessor implements Accessor<PointShadowKey, PointShadowData> {

    @Override
    public PointShadowData access(final PointShadowKey key) {
        return new PointShadowData(key.light, key.resolution);
    }
}
