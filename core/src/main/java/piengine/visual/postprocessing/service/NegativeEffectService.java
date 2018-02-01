package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.NegativeEffectContext;
import piengine.visual.postprocessing.shader.NegativeEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

@Component
public class NegativeEffectService extends AbstractPostProcessingRenderService<NegativeEffectShader, NegativeEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;

    @Wire
    public NegativeEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                 final MeshService meshService, final FramebufferManager framebufferManager,
                                 final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
    }

    @Override
    public NegativeEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, COLOR_TEXTURE_ATTACHMENT);

        return new NegativeEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final NegativeEffectContext context) {
        framebufferManager.bind(context.framebuffer);
        shader.start();
        textureService.bind(inTexture);
        draw();
        shader.stop();
        framebufferManager.unbind();

        return context.framebuffer;
    }

    @Override
    protected NegativeEffectShader createShader() {
        return createShader("negativeEffectShader", NegativeEffectShader.class);
    }

    @Override
    public void cleanUp(final NegativeEffectContext context) {
        framebufferManager.cleanUp(context.framebuffer);
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }
}
