package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.RADIAL_GRADIENT_EFFECT;

public class RadialGradientEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public RadialGradientEffectContext(final Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return RADIAL_GRADIENT_EFFECT;
    }
}
