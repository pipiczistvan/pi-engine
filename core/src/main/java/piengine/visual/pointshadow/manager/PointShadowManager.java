package piengine.visual.pointshadow.manager;

import org.joml.Vector2i;
import piengine.visual.light.domain.Light;
import piengine.visual.pointshadow.domain.PointShadow;
import piengine.visual.pointshadow.domain.PointShadowKey;
import piengine.visual.pointshadow.service.PointShadowService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class PointShadowManager {

    private final PointShadowService pointShadowService;

    @Wire
    public PointShadowManager(final PointShadowService pointShadowService) {
        this.pointShadowService = pointShadowService;
    }

    public PointShadow supply(final Light light, final Vector2i resolution) {
        return pointShadowService.supply(new PointShadowKey(light, resolution));
    }
}
