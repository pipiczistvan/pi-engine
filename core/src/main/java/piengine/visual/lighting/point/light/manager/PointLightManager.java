package piengine.visual.lighting.point.light.manager;

import org.joml.Vector2i;
import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.lighting.point.shadow.domain.PointShadowKey;
import piengine.visual.lighting.point.shadow.service.PointShadowService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class PointLightManager {

    private final PointShadowService pointShadowService;

    @Wire
    public PointLightManager(final PointShadowService pointShadowService) {
        this.pointShadowService = pointShadowService;
    }

    public PointLight supply(final Entity parent, final Color color, final Vector3f attenuation, final Vector2i shadowResolution) {
        PointShadow pointShadow = pointShadowService.supply(new PointShadowKey(shadowResolution));
        PointLight pointLight = new PointLight(parent, color, attenuation, pointShadow);
        pointShadow.initialize(pointLight);

        return pointLight;
    }
}
