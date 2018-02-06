package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.GrayScaleEffectContext;
import piengine.visual.postprocessing.shader.GrayScaleEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.visual.postprocessing.domain.EffectType.GRAY_SCALE_EFFECT;

@Component
public class GrayScaleEffectService extends AbstractPostProcessingRenderService<GrayScaleEffectShader, GrayScaleEffectContext> {

    @Wire
    public GrayScaleEffectService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    public GrayScaleEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .unbind();

        return new GrayScaleEffectContext(framebuffer);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final GrayScaleEffectContext context) {
        context.framebuffer.bind();
        shader.start();
        inFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
        draw();
        shader.stop();
        context.framebuffer.unbind();

        return context.framebuffer;
    }

    @Override
    protected GrayScaleEffectShader createShader() {
        return createShader("grayScaleEffectShader", GrayScaleEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return GRAY_SCALE_EFFECT;
    }
}
