package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.render.shader.HighContrastEffectShader;

public class HighContrastEffect extends PostProcessingEffect<HighContrastEffectShader> {

    public HighContrastEffect(final Framebuffer framebuffer, final HighContrastEffectShader shader) {
        super(framebuffer, shader);
    }

    @Override
    public void useShader() {
    }
}
