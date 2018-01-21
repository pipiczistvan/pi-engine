package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.HORIZONTAL_BLUR_EFFECT;

public class HorizontalBlurEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public HorizontalBlurEffectContext(final Texture inputTexture, final Framebuffer framebuffer) {
        super(inputTexture);
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return HORIZONTAL_BLUR_EFFECT;
    }
}
