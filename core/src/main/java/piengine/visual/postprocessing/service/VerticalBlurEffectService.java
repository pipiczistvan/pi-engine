package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.VerticalBlurEffectContext;
import piengine.visual.postprocessing.shader.VerticalBlurEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.visual.postprocessing.domain.EffectType.VERTICAL_BLUR_EFFECT;

@Component
public class VerticalBlurEffectService extends AbstractPostProcessingRenderService<VerticalBlurEffectShader, VerticalBlurEffectContext> {

    @Wire
    public VerticalBlurEffectService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    public VerticalBlurEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .unbind();

        return new VerticalBlurEffectContext(framebuffer);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final VerticalBlurEffectContext context) {
        context.framebuffer.bind();
        shader.start();
        shader.loadTextureHeight(context.framebuffer.height / 4);
        inFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
        draw();
        shader.stop();
        context.framebuffer.unbind();

        return context.framebuffer;
    }

    @Override
    protected VerticalBlurEffectShader createShader() {
        return createShader("verticalBlurEffectShader", VerticalBlurEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return VERTICAL_BLUR_EFFECT;
    }

}
