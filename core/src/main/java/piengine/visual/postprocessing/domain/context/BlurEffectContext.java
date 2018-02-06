package piengine.visual.postprocessing.domain.context;

import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.BLUR_EFFECT;

public class BlurEffectContext extends PostProcessingEffectContext {

    public final HorizontalBlurEffectContext horizontalBlurEffectContext;
    public final VerticalBlurEffectContext verticalBlurEffectContext;

    public BlurEffectContext(final HorizontalBlurEffectContext horizontalBlurEffectContext, final VerticalBlurEffectContext verticalBlurEffectContext) {
        this.horizontalBlurEffectContext = horizontalBlurEffectContext;
        this.verticalBlurEffectContext = verticalBlurEffectContext;
    }

    @Override
    public EffectType getEffectType() {
        return BLUR_EFFECT;
    }

    @Override
    public void clear() {
        horizontalBlurEffectContext.clear();
        verticalBlurEffectContext.clear();
    }
}
