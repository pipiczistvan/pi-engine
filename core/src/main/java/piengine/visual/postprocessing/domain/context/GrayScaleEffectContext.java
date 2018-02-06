package piengine.visual.postprocessing.domain.context;

import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.GRAY_SCALE_EFFECT;

public class GrayScaleEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public GrayScaleEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return GRAY_SCALE_EFFECT;
    }

    @Override
    public void clear() {
        framebuffer.clear();
    }
}
