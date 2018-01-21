package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.HIGH_CONTRAST_EFFECT;

public class HighContrastEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public HighContrastEffectContext(final Texture inputTexture, final Framebuffer framebuffer) {
        super(inputTexture);
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return HIGH_CONTRAST_EFFECT;
    }
}
