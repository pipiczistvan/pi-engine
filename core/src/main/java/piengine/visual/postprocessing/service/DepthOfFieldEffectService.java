package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.core.base.exception.PIEngineException;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferAttachment;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.BlurEffectContext;
import piengine.visual.postprocessing.domain.context.DepthOfFieldEffectContext;
import piengine.visual.postprocessing.shader.DepthOfFieldEffectShader;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.Set;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.DEPTH_OF_FIELD_EFFECT;

@Component
public class DepthOfFieldEffectService extends AbstractPostProcessingService<DepthOfFieldEffectShader, DepthOfFieldEffectContext> {

    private final FramebufferService framebufferService;
    private final TextureService textureService;
    private final BlurEffectService blurEffectService;

    @Wire
    public DepthOfFieldEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                     final MeshService meshService, final FramebufferService framebufferService,
                                     final TextureService textureService, final BlurEffectService blurEffectService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferService = framebufferService;
        this.textureService = textureService;
        this.blurEffectService = blurEffectService;
    }

    @Override
    public DepthOfFieldEffectContext createContext(final Texture inTexture, final Texture outTexture, final Vector2i outSize) {
        if (!(inTexture instanceof Framebuffer)) {
            throw new PIEngineException("Input texture is not framebuffer!");
        }
        Set<FramebufferAttachment> inFramebufferAttachments = ((Framebuffer) inTexture).getDao().getAttachments().keySet();
        if (!inFramebufferAttachments.contains(COLOR_ATTACHMENT)) {
            throw new PIEngineException("No color attachment bound to framebuffer!");
        }
        if (!inFramebufferAttachments.contains(DEPTH_TEXTURE_ATTACHMENT)) {
            throw new PIEngineException("No depth texture attachment bound to framebuffer!");
        }

        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                outSize,
                outTexture,
                true,
                COLOR_ATTACHMENT
        ));
        BlurEffectContext blurEffectContext = blurEffectService
                .createContext(inTexture, null, outSize);

        return new DepthOfFieldEffectContext(inTexture, framebuffer, blurEffectContext);
    }

    @Override
    protected void render(final DepthOfFieldEffectContext context) {
        blurEffectService.process(context.blurEffectContext);

        framebufferService.bind(context.framebuffer);
        shader.start();
        shader.loadTextureUnits();
        textureService.bind(GL_TEXTURE0, context.inputTexture);
        textureService.bind(GL_TEXTURE1, context.blurEffectContext.framebuffer);
        textureService.bind(GL_TEXTURE2, ((Framebuffer) context.inputTexture).getDao().getAttachment(DEPTH_TEXTURE_ATTACHMENT));
        draw();
        shader.stop();
        framebufferService.unbind();
    }

    @Override
    protected DepthOfFieldEffectShader createShader() {
        return createShader("depthOfFieldEffectShader", DepthOfFieldEffectShader.class);
    }

    @Override
    public EffectType getEffectType() {
        return DEPTH_OF_FIELD_EFFECT;
    }
}
