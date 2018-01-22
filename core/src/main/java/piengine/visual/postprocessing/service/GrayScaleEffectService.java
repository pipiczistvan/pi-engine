package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.GrayScaleEffectContext;
import piengine.visual.postprocessing.shader.GrayScaleEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.GRAY_SCALE_EFFECT;

@Component
public class GrayScaleEffectService extends AbstractPostProcessingRenderService<GrayScaleEffectShader, GrayScaleEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;

    @Wire
    public GrayScaleEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                  final MeshService meshService, final FramebufferManager framebufferManager,
                                  final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
    }

    @Override
    public GrayScaleEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, COLOR_TEXTURE_ATTACHMENT);

        return new GrayScaleEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final GrayScaleEffectContext context) {
        framebufferManager.bind(context.framebuffer);
        shader.start();
        textureService.bind(inTexture);
        draw();
        shader.stop();
        framebufferManager.unbind();

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
