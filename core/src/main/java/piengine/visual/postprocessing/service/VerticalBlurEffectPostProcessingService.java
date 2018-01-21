package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.VerticalBlurEffectContext;
import piengine.visual.postprocessing.shader.VerticalBlurEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.VERTICAL_BLUR_EFFECT;

@Component
public class VerticalBlurEffectPostProcessingService extends AbstractPostProcessingService<VerticalBlurEffectShader, VerticalBlurEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;

    @Wire
    public VerticalBlurEffectPostProcessingService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                                   final MeshService meshService, final FramebufferService framebufferService,
                                                   final TextureService textureService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
    }

    @Override
    public VerticalBlurEffectContext createContext(final Texture inputTexture, final Vector2i size) {
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                size,
                inputTexture,
                true,
                COLOR_ATTACHMENT
        ));

        return new VerticalBlurEffectContext(framebuffer);
    }

    @Override
    protected void render(final VerticalBlurEffectContext context) {
        framebufferService.bind(context.framebuffer);
        shader.start();
        shader.loadTextureHeight(context.framebuffer.getSize().y);
        textureService.bind(context.framebuffer);
        draw();
        shader.stop();
        framebufferService.unbind();
    }

    @Override
    protected VerticalBlurEffectShader createShader() {
        return createShader("verticalBlurEffectShader", VerticalBlurEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return VERTICAL_BLUR_EFFECT;
    }

}
