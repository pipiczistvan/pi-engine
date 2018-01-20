package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.render.shader.NegativeEffectShader;

public class NegativeEffect extends PostProcessingEffect<NegativeEffectShader> {

    public NegativeEffect(final Framebuffer framebuffer, final NegativeEffectShader shader) {
        super(framebuffer, shader);
    }

    @Override
    public void useShader() {
    }
}
