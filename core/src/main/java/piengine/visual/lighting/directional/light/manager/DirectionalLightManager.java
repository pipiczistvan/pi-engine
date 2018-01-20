package piengine.visual.lighting.directional.light.manager;

import org.joml.Vector2i;
import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.camera.domain.Camera;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowKey;
import piengine.visual.lighting.directional.shadow.service.DirectionalShadowService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class DirectionalLightManager {

    private final DirectionalShadowService directionalShadowService;

    @Wire
    public DirectionalLightManager(final DirectionalShadowService directionalShadowService) {
        this.directionalShadowService = directionalShadowService;
    }

    public DirectionalLight supply(final Entity parent, final Color color, final Camera playerCamera, final Vector2i shadowResolution) {
        DirectionalShadow directionalShadow = directionalShadowService.supply(new DirectionalShadowKey(playerCamera, shadowResolution));
        DirectionalLight directionalLight = new DirectionalLight(parent, color, directionalShadow);
        directionalShadow.initialize(directionalLight);

        return directionalLight;
    }

}
