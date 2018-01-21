package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;

import static piengine.visual.postprocessing.domain.EffectType.HIGH_CONTRAST_EFFECT;

public class HighContrastEffectContext implements PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public HighContrastEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return HIGH_CONTRAST_EFFECT;
    }
}
