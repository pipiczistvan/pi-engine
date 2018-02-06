package piengine.visual.postprocessing.domain.context;

import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.HIGH_CONTRAST_EFFECT;

public class HighContrastEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public HighContrastEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return HIGH_CONTRAST_EFFECT;
    }

    @Override
    public void clear() {
        framebuffer.clear();
    }
}
