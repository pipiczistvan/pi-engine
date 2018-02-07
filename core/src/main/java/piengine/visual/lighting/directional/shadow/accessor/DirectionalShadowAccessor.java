package piengine.visual.lighting.directional.shadow.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowData;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowKey;
import puppeteer.annotation.premade.Component;

@Component
public class DirectionalShadowAccessor extends Accessor<DirectionalShadowKey, DirectionalShadowData> {

    @Override
    protected DirectionalShadowData accessResource(final DirectionalShadowKey key) {
        return new DirectionalShadowData(key.playerCamera, key.resolution);
    }

    @Override
    protected String getAccessInfo(final DirectionalShadowKey key, final DirectionalShadowData resource) {
        return String.format("Resolution: %s", key.resolution);
    }
}
