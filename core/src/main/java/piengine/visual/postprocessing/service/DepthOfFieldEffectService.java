package piengine.visual.postprocessing.service;

import org.joml.Vector2i;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
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

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.DEPTH_OF_FIELD_EFFECT;

@Component
public class DepthOfFieldEffectService extends AbstractPostProcessingRenderService<DepthOfFieldEffectShader, DepthOfFieldEffectContext> {

    private final FramebufferManager framebufferManager;
    private final TextureService textureService;
    private final BlurEffectService blurEffectService;

    @Wire
    public DepthOfFieldEffectService(final RenderInterpreter renderInterpreter, final ShaderService shaderService,
                                     final MeshService meshService, final FramebufferManager framebufferManager,
                                     final TextureService textureService, final BlurEffectService blurEffectService) {
        super(renderInterpreter, shaderService, meshService);
        this.framebufferManager = framebufferManager;
        this.textureService = textureService;
        this.blurEffectService = blurEffectService;
    }

    @Override
    public DepthOfFieldEffectContext createContext(final Vector2i outSize) {
        Framebuffer framebuffer = framebufferManager.supply(outSize, false, COLOR_TEXTURE_ATTACHMENT);
        BlurEffectContext blurEffectContext = blurEffectService.createContext(outSize);

        return new DepthOfFieldEffectContext(framebuffer, blurEffectContext);
    }

    @Override
    public Texture process(final Texture inTexture, final DepthOfFieldEffectContext context) {
        Texture blurTexture = blurEffectService.process(inTexture, context.blurEffectContext);
        int depthTextureId = ((Framebuffer) inTexture).getDao().getAttachment(DEPTH_TEXTURE_ATTACHMENT);

        framebufferManager.bind(context.framebuffer);
        shader.start();
        shader.loadTextureUnits();
        textureService.bind(GL_TEXTURE0, inTexture);
        textureService.bind(GL_TEXTURE1, blurTexture);
        textureService.bind(GL_TEXTURE2, depthTextureId);
        draw();
        shader.stop();
        framebufferManager.unbind();

        return context.framebuffer;
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
