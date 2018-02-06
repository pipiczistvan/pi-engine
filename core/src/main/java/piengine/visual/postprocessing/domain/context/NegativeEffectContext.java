package piengine.visual.postprocessing.domain.context;

import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

public class NegativeEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public NegativeEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }

    @Override
    public void clear() {
        framebuffer.clear();
    }
}
