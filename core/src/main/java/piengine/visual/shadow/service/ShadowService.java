package piengine.visual.shadow.service;

import piengine.core.base.api.Updatable;
import piengine.core.base.resource.SupplierService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.shadow.accessor.ShadowAccessor;
import piengine.visual.shadow.domain.Shadow;
import piengine.visual.shadow.domain.ShadowDao;
import piengine.visual.shadow.domain.ShadowData;
import piengine.visual.shadow.domain.ShadowKey;
import piengine.visual.shadow.interpreter.ShadowInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;

@Component
public class ShadowService extends SupplierService<ShadowKey, ShadowData, ShadowDao, Shadow> implements Updatable {

    private final FramebufferManager framebufferManager;

    @Wire
    public ShadowService(final ShadowAccessor shadowAccessor, final ShadowInterpreter shadowInterpreter,
                         final FramebufferManager framebufferManager) {
        super(shadowAccessor, shadowInterpreter);

        this.framebufferManager = framebufferManager;
    }

    @Override
    protected Shadow createDomain(final ShadowDao dao, final ShadowData resource) {
        Framebuffer shadowMap = framebufferManager.supply(resource.resolution, false, DEPTH_TEXTURE_ATTACHMENT);

        return new Shadow(dao, resource.playerCamera, resource.light, shadowMap);
    }

    @Override
    public void update(final float delta) {
        for (Shadow shadow : getDomainValues()) {
            shadow.update(delta);
        }
    }
}
