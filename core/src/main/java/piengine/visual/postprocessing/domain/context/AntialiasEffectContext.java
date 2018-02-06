package piengine.visual.postprocessing.domain.context;

import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

public class AntialiasEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public AntialiasEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return ANTIALIAS_EFFECT;
    }

    @Override
    public void clear() {
        framebuffer.clear();
    }
}
