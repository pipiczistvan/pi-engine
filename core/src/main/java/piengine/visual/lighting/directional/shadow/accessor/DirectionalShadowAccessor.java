package piengine.visual.lighting.directional.shadow.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowData;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowKey;
import puppeteer.annotation.premade.Component;

@Component
public class DirectionalShadowAccessor implements Accessor<DirectionalShadowKey, DirectionalShadowData> {

    @Override
    public DirectionalShadowData access(final DirectionalShadowKey key) {
        return new DirectionalShadowData(key.playerCamera, key.resolution);
    }
}
