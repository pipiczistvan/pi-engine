package piengine.visual.lighting.directional.shadow.service;

import piengine.core.architecture.service.SupplierService;
import piengine.core.base.api.Updatable;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowKey;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DirectionalShadowService extends SupplierService<DirectionalShadowKey, DirectionalShadow> implements Updatable {

    private final List<DirectionalShadow> directionalShadows;

    public DirectionalShadowService() {
        this.directionalShadows = new ArrayList<>();
    }

    @Override
    public DirectionalShadow supply(final DirectionalShadowKey key) {
        Framebuffer shadowMap = new Framebuffer(key.resolution.x, key.resolution.y)
                .bind()
                .setReadBuffer(false)
                .setDrawBuffer(false)
                .attachDepthTexture()
                .unbind();

        DirectionalShadow directionalShadow = new DirectionalShadow(key.playerCamera, shadowMap);
        directionalShadows.add(directionalShadow);

        return directionalShadow;
    }

    @Override
    public void update(final float delta) {
        directionalShadows.forEach(directionalShadow -> directionalShadow.update(delta));
    }

    @Override
    public void terminate() {
        directionalShadows.forEach(directionalShadow -> directionalShadow.getShadowMap().clear());
    }
}
