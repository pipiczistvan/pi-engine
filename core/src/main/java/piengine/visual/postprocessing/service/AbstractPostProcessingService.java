package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.Effective;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;

public abstract class AbstractPostProcessingService<C extends PostProcessingEffectContext> implements Effective {

    public abstract Framebuffer process(final Framebuffer inFramebuffer, final C context);

    public abstract C createContext(final Vector2i outSize);
}
