package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.VERTICAL_BLUR_EFFECT;

public class VerticalBlurEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public VerticalBlurEffectContext(final Texture inputTexture, final Framebuffer framebuffer) {
        super(inputTexture);
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return VERTICAL_BLUR_EFFECT;
    }
}
