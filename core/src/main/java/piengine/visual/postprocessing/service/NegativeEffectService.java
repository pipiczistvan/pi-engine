package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.NegativeEffectContext;
import piengine.visual.postprocessing.shader.NegativeEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.NEGATIVE_EFFECT;

@Component
public class NegativeEffectService extends AbstractPostProcessingService<NegativeEffectShader, NegativeEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;

    @Wire
    public NegativeEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                 final MeshService meshService, final FramebufferService framebufferService,
                                 final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
    }

    @Override
    public NegativeEffectContext createContext(final Texture inTexture, final Texture outTexture, final Vector2i outSize) {
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                outSize,
                outTexture,
                true,
                COLOR_ATTACHMENT
        ));

        return new NegativeEffectContext(inTexture, framebuffer);
    }

    @Override
    protected void render(final NegativeEffectContext context) {
        framebufferService.bind(context.framebuffer);
        shader.start();
        textureService.bind(context.inputTexture);
        draw();
        shader.stop();
        framebufferService.unbind();
    }

    @Override
    protected NegativeEffectShader createShader() {
        return createShader("negativeEffectShader", NegativeEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return NEGATIVE_EFFECT;
    }
}
