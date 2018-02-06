package piengine.visual.postprocessing.domain.context;

import org.joml.Vector2f;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

import static piengine.visual.postprocessing.domain.EffectType.DEPTH_OF_FIELD_EFFECT;

public class DepthOfFieldEffectContext extends PostProcessingEffectContext {

    public final Framebuffer framebuffer;
    public final BlurEffectContext blurEffectContext;
    public final Vector2f textureCenter;

    public DepthOfFieldEffectContext(final Framebuffer framebuffer, final BlurEffectContext blurEffectContext) {
        this.framebuffer = framebuffer;
        this.blurEffectContext = blurEffectContext;
        this.textureCenter = new Vector2f(framebuffer.width/ 2, framebuffer.height / 2);
    }

    @Override
    public EffectType getEffectType() {
        return DEPTH_OF_FIELD_EFFECT;
    }

    @Override
    public void clear() {
        framebuffer.clear();
        blurEffectContext.clear();
    }
}
