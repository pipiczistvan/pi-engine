package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.RadialGradientEffectContext;
import piengine.visual.postprocessing.shader.RadialGradientEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.RADIAL_GRADIENT_EFFECT;

@Component
public class RadialGradientEffectService extends AbstractPostProcessingRenderService<RadialGradientEffectShader, RadialGradientEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;

    @Wire
    public RadialGradientEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                       final MeshService meshService, final FramebufferManager framebufferManager,
                                       final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
    }

    @Override
    public RadialGradientEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, false, COLOR_TEXTURE_ATTACHMENT);

        return new RadialGradientEffectContext(framebuffer);
    }

    @Override
    public Texture process(final Texture inTexture, final RadialGradientEffectContext context) {
        framebufferManager.bind(context.framebuffer);
        shader.start();
        textureService.bind(inTexture);
        draw();
        shader.stop();
        framebufferManager.unbind();

        return context.framebuffer;
    }

    @Override
    protected RadialGradientEffectShader createShader() {
        return createShader("radialGradientEffectShader", RadialGradientEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return RADIAL_GRADIENT_EFFECT;
    }
}
