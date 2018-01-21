package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.BlurEffectContext;
import piengine.visual.postprocessing.domain.context.HorizontalBlurEffectContext;
import piengine.visual.postprocessing.domain.context.VerticalBlurEffectContext;
import piengine.visual.postprocessing.shader.BlurEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.BLUR_EFFECT;

@Component
public class BlurEffectPostProcessingService extends AbstractPostProcessingService<BlurEffectShader, BlurEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;
    private final HorizontalBlurEffectPostProcessingService horizontalBlurEffectPostProcessingService;
    private VerticalBlurEffectPostProcessingService verticalBlurEffectPostProcessingService;

    @Wire
    public BlurEffectPostProcessingService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                           final MeshService meshService, final FramebufferService framebufferService,
                                           final TextureService textureService, final HorizontalBlurEffectPostProcessingService horizontalBlurEffectPostProcessingService,
                                           final VerticalBlurEffectPostProcessingService verticalBlurEffectPostProcessingService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
        this.horizontalBlurEffectPostProcessingService = horizontalBlurEffectPostProcessingService;
        this.verticalBlurEffectPostProcessingService = verticalBlurEffectPostProcessingService;
    }

    @Override
    public BlurEffectContext createContext(final Texture inputTexture, final Vector2i size) {
        Vector2i blurTextureSize = new Vector2i(size.x / 4, size.y / 4);
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                blurTextureSize,
                inputTexture,
                true,
                COLOR_ATTACHMENT
        ));
        HorizontalBlurEffectContext horizontalBlurEffectContext = horizontalBlurEffectPostProcessingService
                .createContext(framebuffer, blurTextureSize);
        VerticalBlurEffectContext verticalBlurEffectContext = verticalBlurEffectPostProcessingService
                .createContext(framebuffer, blurTextureSize);

        return new BlurEffectContext(framebuffer, horizontalBlurEffectContext, verticalBlurEffectContext);
    }

    @Override
    protected void render(final BlurEffectContext context) {
        horizontalBlurEffectPostProcessingService.process(context.horizontalBlurEffectContext);
        verticalBlurEffectPostProcessingService.process(context.verticalBlurEffectContext);

        framebufferService.bind(context.framebuffer);
        shader.start();
        textureService.bind(context.framebuffer);
        draw();
        shader.stop();
        framebufferService.unbind();
    }

    @Override
    protected BlurEffectShader createShader() {
        return createShader("blurEffectShader", BlurEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return BLUR_EFFECT;
    }
}
