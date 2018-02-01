package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.visual.postprocessing.domain.Effective;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.texture.domain.Texture;

public abstract class AbstractPostProcessingService<C extends PostProcessingEffectContext> implements Effective {

    public abstract Texture process(final Texture inTexture, final C context);

    public abstract C createContext(final Vector2i outSize);

    public abstract void cleanUp(final C context);
}
