package piengine.visual.postprocessing.domain.context;

import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.HORIZONTAL_BLUR_EFFECT;

public class HorizontalBlurEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public HorizontalBlurEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return HORIZONTAL_BLUR_EFFECT;
    }

    @Override
    public void clear() {
        framebuffer.clear();
    }
}
