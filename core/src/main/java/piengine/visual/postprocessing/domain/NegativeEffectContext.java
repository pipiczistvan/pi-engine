package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;

import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

public class NegativeEffectContext implements PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public NegativeEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }
}
