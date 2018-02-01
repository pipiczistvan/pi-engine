package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.HorizontalBlurEffectContext;
import piengine.visual.postprocessing.shader.HorizontalBlurEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.HORIZONTAL_BLUR_EFFECT;

@Component
public class HorizontalBlurEffectService extends AbstractPostProcessingRenderService<HorizontalBlurEffectShader, HorizontalBlurEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;

    @Wire
    public HorizontalBlurEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                       final MeshService meshService, final FramebufferManager framebufferManager,
                                       final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
    }

    @Override
    public HorizontalBlurEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, COLOR_TEXTURE_ATTACHMENT);

        return new HorizontalBlurEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final HorizontalBlurEffectContext context) {
        framebufferManager.bind(context.framebuffer);
        shader.start();
        shader.loadTextureWidth(context.framebuffer.getSize().x / 4);
        textureService.bind(inTexture);
        draw();
        shader.stop();
        framebufferManager.unbind();

        return context.framebuffer;
    }

    @Override
    protected HorizontalBlurEffectShader createShader() {
        return createShader("horizontalBlurEffectShader", HorizontalBlurEffectShader.class);
    }

    @Override
    public void cleanUp(final HorizontalBlurEffectContext context) {
        framebufferManager.cleanUp(context.framebuffer);
    }

    @Override
    public EffectType getEffectType() {
        return HORIZONTAL_BLUR_EFFECT;
    }

}
