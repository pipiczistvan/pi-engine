package piengine.visual.postprocessing.domain.context;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

public class NegativeEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;

    public NegativeEffectContext(final Texture inputTexture, final Framebuffer framebuffer) {
        super(inputTexture);
        this.framebuffer = framebuffer;
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }
}
