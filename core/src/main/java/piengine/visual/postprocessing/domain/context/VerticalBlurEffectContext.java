package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.VERTICAL_BLUR_EFFECT;

public class VerticalBlurEffectContext implements PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public VerticalBlurEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return VERTICAL_BLUR_EFFECT;
    }
}
