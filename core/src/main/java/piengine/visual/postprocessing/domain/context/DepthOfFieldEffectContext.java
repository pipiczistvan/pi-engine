package piengine.visual.postprocessing.domain.context;

import org.joml.Vector2f;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

import static piengine.visual.postprocessing.domain.EffectType.DEPTH_OF_FIELD_EFFECT;

public class DepthOfFieldEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;
    public final BlurEffectContext blurEffectContext;
    public final Vector2f textureCenter;

    public DepthOfFieldEffectContext(final Texture inputTexture, final Framebuffer framebuffer,
                                     final BlurEffectContext blurEffectContext) {
        super(inputTexture);
        this.framebuffer = framebuffer;
        this.blurEffectContext = blurEffectContext;
        this.textureCenter = new Vector2f(framebuffer.getSize().x / 2, framebuffer.getSize().y / 2);
    }

    @Override
    public EffectType getEffectType() {
        return DEPTH_OF_FIELD_EFFECT;
    }
}
