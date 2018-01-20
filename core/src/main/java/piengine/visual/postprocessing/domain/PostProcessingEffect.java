package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.shader.domain.Shader;

public abstract class PostProcessingEffect<S extends Shader> {

    public final Framebuffer framebuffer;
    public final S shader;

    public PostProcessingEffect(final Framebuffer framebuffer, final S shader) {
        this.framebuffer = framebuffer;
        this.shader = shader;
    }

    public abstract void useShader();
}
