package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.AntialiasEffectContext;
import puppeteer.annotation.premade.Component;

import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

@Component
public class AntialiasEffectService extends AbstractPostProcessingService<AntialiasEffectContext> {

    @Override
    public AntialiasEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .attachDepthTexture()
                .unbind();

        return new AntialiasEffectContext(framebuffer);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final AntialiasEffectContext context) {
        inFramebuffer.blit(context.framebuffer);

        return context.framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return ANTIALIAS_EFFECT;
    }
}
