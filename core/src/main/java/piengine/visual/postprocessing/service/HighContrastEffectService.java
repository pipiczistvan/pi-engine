package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.HighContrastEffectContext;
import piengine.visual.postprocessing.shader.HighContrastEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.HIGH_CONTRAST_EFFECT;

@Component
public class HighContrastEffectService extends AbstractPostProcessingRenderService<HighContrastEffectShader, HighContrastEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;

    @Wire
    public HighContrastEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                     final MeshService meshService, final FramebufferManager framebufferManager,
                                     final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
    }

    @Override
    public HighContrastEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, COLOR_TEXTURE_ATTACHMENT);

        return new HighContrastEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final HighContrastEffectContext context) {
        framebufferManager.bind(context.framebuffer);
        shader.start();
        textureService.bind(inTexture);
        draw();
        shader.stop();
        framebufferManager.unbind();

        return context.framebuffer;
    }

    @Override
    protected HighContrastEffectShader createShader() {
        return createShader("highContrastEffectShader", HighContrastEffectShader.class);
    }

    @Override
    public void cleanUp(final HighContrastEffectContext context) {
        framebufferManager.cleanUp(context.framebuffer);
    }

    @Override
    public EffectType getEffectType() {
        return HIGH_CONTRAST_EFFECT;
    }
}
