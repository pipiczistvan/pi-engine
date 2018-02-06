package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.BlurEffectContext;
import piengine.visual.postprocessing.domain.context.DepthOfFieldEffectContext;
import piengine.visual.postprocessing.shader.DepthOfFieldEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.DEPTH;
import static piengine.visual.postprocessing.domain.EffectType.DEPTH_OF_FIELD_EFFECT;

@Component
public class DepthOfFieldEffectService extends AbstractPostProcessingRenderService<DepthOfFieldEffectShader, DepthOfFieldEffectContext> {

    private final BlurEffectService blurEffectService;

    @Wire
    public DepthOfFieldEffectService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader, final BlurEffectService blurEffectService) {
        super(renderInterpreter, glslLoader);
        this.blurEffectService = blurEffectService;
    }

    @Override
    public DepthOfFieldEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = new Framebuffer(outSize.x, outSize.y)
                .bind()
                .attachColorTexture()
                .unbind();
        BlurEffectContext blurEffectContext = blurEffectService.createContext(outSize);

        return new DepthOfFieldEffectContext(framebuffer, blurEffectContext);
    }

    @Override
    public Framebuffer process(final Framebuffer inFramebuffer, final DepthOfFieldEffectContext context) {
        Framebuffer blurTexture = blurEffectService.process(inFramebuffer, context.blurEffectContext);

        context.framebuffer.bind();
        shader.start();
        shader.loadTextureUnits();
        inFramebuffer.getTextureAttachment(COLOR).bind(GL_TEXTURE0);
        blurTexture.getTextureAttachment(COLOR).bind(GL_TEXTURE1);
        inFramebuffer.getTextureAttachment(DEPTH).bind(GL_TEXTURE2);
        draw();
        shader.stop();
        context.framebuffer.unbind();

        return context.framebuffer;
    }

    @Override
    protected DepthOfFieldEffectShader createShader() {
        return createShader("depthOfFieldEffectShader", DepthOfFieldEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return DEPTH_OF_FIELD_EFFECT;
    }
}
