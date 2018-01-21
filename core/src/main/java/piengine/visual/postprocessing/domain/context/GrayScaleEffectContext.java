package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.GRAY_SCALE_EFFECT;

public class GrayScaleEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public GrayScaleEffectContext(final Texture inputTexture, final Framebuffer framebuffer) {
        super(inputTexture);
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return GRAY_SCALE_EFFECT;
    }
}
