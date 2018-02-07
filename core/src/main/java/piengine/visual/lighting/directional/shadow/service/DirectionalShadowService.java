package piengine.visual.lighting.directional.shadow.service;

import piengine.core.base.api.Updatable;
import piengine.core.base.resource.SupplierService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.lighting.directional.shadow.accessor.DirectionalShadowAccessor;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadow;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowDao;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowData;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowKey;
import piengine.visual.lighting.directional.shadow.interpreter.DirectionalShadowInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;

@Component
public class DirectionalShadowService extends SupplierService<DirectionalShadowKey, DirectionalShadowData, DirectionalShadowDao, DirectionalShadow> implements Updatable {

    private final FramebufferManager framebufferManager;

    @Wire
    public DirectionalShadowService(final DirectionalShadowAccessor directionalShadowAccessor, final DirectionalShadowInterpreter directionalShadowInterpreter,
                                    final FramebufferManager framebufferManager) {
        super(directionalShadowAccessor, directionalShadowInterpreter);

        this.framebufferManager = framebufferManager;
    }

    @Override
    protected DirectionalShadow createDomain(final DirectionalShadowDao dao, final DirectionalShadowData resource) {
        Framebuffer shadowMap = framebufferManager.supply(resource.resolution, true, DEPTH_TEXTURE_ATTACHMENT);

        return new DirectionalShadow(dao, resource.playerCamera, shadowMap);
    }

    @Override
    public void update(final float delta) {
        for (DirectionalShadow directionalShadow : domainMap.values()) {
            directionalShadow.update(delta);
        }
    }
}
