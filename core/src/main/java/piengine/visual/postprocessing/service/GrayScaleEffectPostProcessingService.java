package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.GrayScaleEffectContext;
import piengine.visual.postprocessing.shader.GrayScaleEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.GRAY_SCALE_EFFECT;

@Component
public class GrayScaleEffectPostProcessingService extends AbstractPostProcessingService<GrayScaleEffectShader, GrayScaleEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;

    @Wire
    public GrayScaleEffectPostProcessingService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                                final MeshService meshService, final FramebufferService framebufferService,
                                                final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
    }

    @Override
    public GrayScaleEffectContext createContext(final Texture inputTexture, final Vector2i size) {
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                size,
                inputTexture,
                true,
                COLOR_ATTACHMENT
        ));

        return new GrayScaleEffectContext(framebuffer);
    }

    @Override
    protected void render(final GrayScaleEffectContext context) {
        framebufferService.bind(context.framebuffer);
        shader.start();
        textureService.bind(context.framebuffer);
        draw();
        shader.stop();
        framebufferService.unbind();
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
