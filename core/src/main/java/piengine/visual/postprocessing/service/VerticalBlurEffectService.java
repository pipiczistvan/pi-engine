package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.VerticalBlurEffectContext;
import piengine.visual.postprocessing.shader.VerticalBlurEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.VERTICAL_BLUR_EFFECT;

@Component
public class VerticalBlurEffectService extends AbstractPostProcessingRenderService<VerticalBlurEffectShader, VerticalBlurEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;

    @Wire
    public VerticalBlurEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                     final MeshService meshService, final FramebufferManager framebufferManager,
                                     final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
    }

    @Override
    public VerticalBlurEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, COLOR_TEXTURE_ATTACHMENT);

        return new VerticalBlurEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final VerticalBlurEffectContext context) {
        framebufferManager.bind(context.framebuffer);
        shader.start();
        shader.loadTextureHeight(context.framebuffer.getSize().y / 4);
        textureService.bind(inTexture);
        draw();
        shader.stop();
        framebufferManager.unbind();

        return context.framebuffer;
    }

    @Override
    protected VerticalBlurEffectShader createShader() {
        return createShader("verticalBlurEffectShader", VerticalBlurEffectShader.class);
    }

    @Override
    public void cleanUp(final VerticalBlurEffectContext context) {
        framebufferManager.cleanUp(context.framebuffer);
    }

    @Override
    public EffectType getEffectType() {
        return VERTICAL_BLUR_EFFECT;
    }

}
