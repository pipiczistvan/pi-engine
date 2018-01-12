package piengine.visual.shadow.service;

import org.joml.Vector2i;
import piengine.core.base.api.Updatable;
import piengine.core.base.resource.SupplierService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
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

    private final FramebufferService framebufferService;

    @Wire
    public ShadowService(final ShadowAccessor shadowAccessor, final ShadowInterpreter shadowInterpreter,
                         final FramebufferService framebufferService) {
        super(shadowAccessor, shadowInterpreter);

        this.framebufferService = framebufferService;
    }

    @Override
    protected Shadow createDomain(final ShadowDao dao, final ShadowData resource) {
        Framebuffer shadowMap = framebufferService.supply(new FramebufferKey(
                new Vector2i(resource.resolution),
                false,
                DEPTH_TEXTURE_ATTACHMENT
        ));

        return new Shadow(dao, resource.playerCamera, resource.light, shadowMap);
    }

    @Override
    public void update(double delta) {
        for (Shadow shadow : getValues()) {
            shadow.update(delta);
        }
    }
}
