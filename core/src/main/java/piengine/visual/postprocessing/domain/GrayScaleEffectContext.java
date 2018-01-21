package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;

import static piengine.visual.postprocessing.domain.EffectType.GRAY_SCALE_EFFECT;

public class GrayScaleEffectContext implements PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public GrayScaleEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return GRAY_SCALE_EFFECT;
    }
}
