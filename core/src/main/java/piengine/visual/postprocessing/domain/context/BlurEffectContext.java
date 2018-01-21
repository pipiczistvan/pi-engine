package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.BLUR_EFFECT;

public class BlurEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;
    public final HorizontalBlurEffectContext horizontalBlurEffectContext;
    public final VerticalBlurEffectContext verticalBlurEffectContext;

    public BlurEffectContext(final Texture inputTexture, final Framebuffer framebuffer,
                             final HorizontalBlurEffectContext horizontalBlurEffectContext, final VerticalBlurEffectContext verticalBlurEffectContext) {
        super(inputTexture);
        this.framebuffer = framebuffer;
        this.horizontalBlurEffectContext = horizontalBlurEffectContext;
        this.verticalBlurEffectContext = verticalBlurEffectContext;
    }

    @Override
    public EffectType getEffectType() {
        return BLUR_EFFECT;
    }
}
