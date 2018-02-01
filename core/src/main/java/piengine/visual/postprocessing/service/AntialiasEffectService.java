package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.AntialiasEffectContext;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

@Component
public class AntialiasEffectService extends AbstractPostProcessingService<AntialiasEffectContext> {

    private final FramebufferManager framebufferManager;

    @Wire
    public AntialiasEffectService(final FramebufferManager framebufferManager) {
        this.framebufferManager = framebufferManager;
    }

    @Override
    public AntialiasEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, COLOR_TEXTURE_ATTACHMENT, DEPTH_TEXTURE_ATTACHMENT);

        return new AntialiasEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final AntialiasEffectContext context) {
        framebufferManager.blit((Framebuffer) inTexture, context.framebuffer);

        return context.framebuffer;
    }

    @Override
    public void cleanUp(final AntialiasEffectContext context) {
        framebufferManager.cleanUp(context.framebuffer);
    }

    @Override
    public EffectType getEffectType() {
        return ANTIALIAS_EFFECT;
    }
}
