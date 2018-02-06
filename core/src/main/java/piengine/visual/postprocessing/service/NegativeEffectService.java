package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.NegativeEffectContext;
import piengine.visual.postprocessing.shader.NegativeEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

@Component
public class NegativeEffectService extends AbstractPostProcessingRenderService<NegativeEffectShader, NegativeEffectContext> {

    @Wire
    public NegativeEffectService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    public NegativeEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .unbind();

        return new NegativeEffectContext(framebuffer);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final NegativeEffectContext context) {
        context.framebuffer.bind();
        shader.start();
        inFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
        draw();
        shader.stop();
        context.framebuffer.unbind();

        return context.framebuffer;
    }

    @Override
    protected NegativeEffectShader createShader() {
        return createShader("negativeEffectShader", NegativeEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }
}
