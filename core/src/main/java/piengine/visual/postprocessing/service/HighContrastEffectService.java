package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.HighContrastEffectContext;
import piengine.visual.postprocessing.shader.HighContrastEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.visual.postprocessing.domain.EffectType.HIGH_CONTRAST_EFFECT;

@Component
public class HighContrastEffectService extends AbstractPostProcessingRenderService<HighContrastEffectShader, HighContrastEffectContext> {

    @Wire
    public HighContrastEffectService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    public HighContrastEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .unbind();

        return new HighContrastEffectContext(framebuffer);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final HighContrastEffectContext context) {
        context.framebuffer.bind();
        shader.start();
        inFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
        draw();
        shader.stop();
        context.framebuffer.unbind();

        return context.framebuffer;
    }

    @Override
    protected HighContrastEffectShader createShader() {
        return createShader("highContrastEffectShader", HighContrastEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return HIGH_CONTRAST_EFFECT;
    }
}
