package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.HorizontalBlurEffectContext;
import piengine.visual.postprocessing.shader.HorizontalBlurEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.visual.postprocessing.domain.EffectType.HORIZONTAL_BLUR_EFFECT;

@Component
public class HorizontalBlurEffectService extends AbstractPostProcessingRenderService<HorizontalBlurEffectShader, HorizontalBlurEffectContext> {

    @Wire
    public HorizontalBlurEffectService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    public HorizontalBlurEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .unbind();

        return new HorizontalBlurEffectContext(framebuffer);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final HorizontalBlurEffectContext context) {
        context.framebuffer.bind();
        shader.start();
        shader.loadTextureWidth(context.framebuffer.width / 4);
        inFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
        draw();
        shader.stop();
        context.framebuffer.unbind();

        return context.framebuffer;
    }

    @Override
    protected HorizontalBlurEffectShader createShader() {
        return createShader("horizontalBlurEffectShader", HorizontalBlurEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return HORIZONTAL_BLUR_EFFECT;
    }

}
