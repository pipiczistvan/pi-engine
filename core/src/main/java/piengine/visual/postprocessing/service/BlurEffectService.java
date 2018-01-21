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
public class BlurEffectService extends AbstractPostProcessingService<BlurEffectShader, BlurEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;
    private final HorizontalBlurEffectService horizontalBlurEffectService;
    private final VerticalBlurEffectService verticalBlurEffectService;

    @Wire
    public BlurEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                             final MeshService meshService, final FramebufferService framebufferService,
                             final TextureService textureService, final HorizontalBlurEffectService horizontalBlurEffectService,
                             final VerticalBlurEffectService verticalBlurEffectService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
        this.horizontalBlurEffectService = horizontalBlurEffectService;
        this.verticalBlurEffectService = verticalBlurEffectService;
    }

    @Override
    public BlurEffectContext createContext(final Texture inTexture, final Texture outTexture, final Vector2i outSize) {
        Vector2i blurTextureSize = new Vector2i(outSize.x / 4, outSize.y / 4);
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                outSize,
                outTexture,
                true,
                COLOR_ATTACHMENT
        ));
        HorizontalBlurEffectContext horizontalBlurEffectContext = horizontalBlurEffectService
                .createContext(inTexture, framebuffer, blurTextureSize);
        VerticalBlurEffectContext verticalBlurEffectContext = verticalBlurEffectService
                .createContext(framebuffer, framebuffer, blurTextureSize);

        return new BlurEffectContext(framebuffer, framebuffer, horizontalBlurEffectContext, verticalBlurEffectContext);
    }

    @Override
    protected void render(final BlurEffectContext context) {
        horizontalBlurEffectService.process(context.horizontalBlurEffectContext);
        verticalBlurEffectService.process(context.verticalBlurEffectContext);

        framebufferService.bind(context.framebuffer);
        shader.start();
        textureService.bind(context.inputTexture);
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
