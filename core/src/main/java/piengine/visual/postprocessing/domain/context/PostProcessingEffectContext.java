package piengine.visual.postprocessing.domain.context;

import piengine.visual.postprocessing.domain.Effective;
import piengine.visual.texture.domain.Texture;

public abstract class PostProcessingEffectContext implements Effective {

    public final Texture inputTexture;

    public PostProcessingEffectContext(final Texture inputTexture) {
        this.inputTexture = inputTexture;
    }
}
