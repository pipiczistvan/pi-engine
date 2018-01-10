package piengine.visual.shadow.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.shadow.domain.ShadowData;
import piengine.visual.shadow.domain.ShadowKey;
import puppeteer.annotation.premade.Component;

@Component
public class ShadowAccessor implements Accessor<ShadowKey, ShadowData> {

    @Override
    public ShadowData access(final ShadowKey key) {
        return new ShadowData(key.playerCamera, key.light, key.resolution);
    }
}
