package piengine.visual.postprocessing.service;

import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.NegativeEffectContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.NegativeEffectShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

@Component
public class NegativeEffectPostProcessingService extends AbstractPostProcessingService<NegativeEffectShader, NegativeEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;

    @Wire
    public NegativeEffectPostProcessingService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                               final MeshService meshService, final FramebufferService framebufferService,
                                               final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
    }

    @Override
    public NegativeEffectContext createContext(final Texture inputTexture) {
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                inputTexture.getSize(),
                inputTexture,
                true,
                COLOR_ATTACHMENT
        ));

        return new NegativeEffectContext(framebuffer);
    }

    @Override
    protected void render(final NegativeEffectContext context) {
        framebufferService.bind(context.framebuffer);
        shader.start();
        textureService.bind(context.framebuffer);
        draw();
        shader.stop();
        framebufferService.unbind();
    }

    @Override
    protected NegativeEffectShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("negativeEffectShader")).castTo(NegativeEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }
}
