package piengine.visual.postprocessing.domain;

import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.render.shader.GrayScaleEffectShader;

public class GrayScaleEffect extends PostProcessingEffect<GrayScaleEffectShader> {

    public GrayScaleEffect(final Framebuffer framebuffer, final GrayScaleEffectShader shader) {
        super(framebuffer, shader);
    }

    @Override
    public void useShader() {
    }
}
