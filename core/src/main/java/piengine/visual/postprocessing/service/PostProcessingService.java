package piengine.visual.postprocessing.service;

import piengine.core.base.api.Initializable;
import piengine.core.base.api.Service;
import piengine.core.base.exception.PIEngineException;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.service.MeshService;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.GrayScaleEffect;
import piengine.visual.postprocessing.domain.HighContrastEffect;
import piengine.visual.postprocessing.domain.NegativeEffect;
import piengine.visual.postprocessing.domain.PostProcessingEffect;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.GrayScaleEffectShader;
import piengine.visual.render.shader.HighContrastEffectShader;
import piengine.visual.render.shader.NegativeEffectShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.RENDER_BUFFER_ATTACHMENT;

@Component
public class PostProcessingService implements Service, Initializable {

    private final FramebufferService framebufferService;
    private final ShaderService shaderService;
    private final TextureService textureService;
    private final MeshService meshService;
    private final RenderInterpreter renderInterpreter;

    private Mesh canvas;

    @Wire
    public PostProcessingService(final FramebufferService framebufferService, final ShaderService shaderService,
                                 final TextureService textureService, final MeshService meshService,
                                 final RenderInterpreter renderInterpreter) {
        this.framebufferService = framebufferService;
        this.shaderService = shaderService;
        this.textureService = textureService;
        this.meshService = meshService;
        this.renderInterpreter = renderInterpreter;
    }

    @Override
    public void initialize() {
        canvas = meshService.supply(new MeshKey("canvas"));
    }

    public void process(final PostProcessingEffect effect) {
        framebufferService.bind(effect.framebuffer);
        effect.shader.start();
        effect.useShader();
        textureService.bind(effect.framebuffer);
        draw();
        effect.shader.stop();
        framebufferService.unbind();
    }

    public PostProcessingEffect supply(final EffectType effectType, final Texture texture) {
        Framebuffer framebuffer = framebufferService.supply(new FramebufferKey(
                texture.getSize(),
                texture,
                true,
                COLOR_ATTACHMENT,
                RENDER_BUFFER_ATTACHMENT
        ));

        switch (effectType) {
            case HIGH_CONTRAST_EFFECT:
                HighContrastEffectShader highContrastEffectShader = shaderService.supply(new ShaderKey("highContrastEffectShader")).castTo(HighContrastEffectShader.class);
                return new HighContrastEffect(framebuffer, highContrastEffectShader);
            case GRAY_SCALE_EFFECT:
                GrayScaleEffectShader grayScaleEffectShader = shaderService.supply(new ShaderKey("grayScaleEffectShader")).castTo(GrayScaleEffectShader.class);
                return new GrayScaleEffect(framebuffer, grayScaleEffectShader);
            case NEGATIVE_EFFECT:
                NegativeEffectShader negativeEffectShader = shaderService.supply(new ShaderKey("negativeEffectShader")).castTo(NegativeEffectShader.class);
                return new NegativeEffect(framebuffer, negativeEffectShader);
            default:
                throw new PIEngineException("Unknown effect type $s!", effectType);
        }
    }

    private void draw() {
        renderInterpreter.bindVertexArray(canvas.getDao().vaoId);
        renderInterpreter.enableVertexAttribArray(canvas.getDao().getVertexAttribs());
        renderInterpreter.drawElements(GL_TRIANGLES, canvas.getDao().vertexCount);
        renderInterpreter.disableVertexAttribArray(canvas.getDao().getVertexAttribs());
        renderInterpreter.unbindVertexArray();
    }
}
