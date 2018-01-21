package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.HORIZONTAL_BLUR_EFFECT;

public class HorizontalBlurEffectContext implements PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public HorizontalBlurEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return HORIZONTAL_BLUR_EFFECT;
    }
}
