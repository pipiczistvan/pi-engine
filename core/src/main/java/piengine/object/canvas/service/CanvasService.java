package piengine.object.canvas.service;

import org.joml.Vector2i;
import piengine.core.architecture.service.SupplierService;
import piengine.core.base.api.Initializable;
import piengine.core.base.exception.PIEngineException;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.postprocessing.service.AbstractPostProcessingService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class CanvasService extends SupplierService<CanvasKey, Canvas> implements Initializable {

    private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};

    private final List<AbstractPostProcessingService> postProcessingServices;
    private final List<PostProcessingEffectContext> effectContextList;
    private VertexArray canvasVertexArray;

    @Wire
    public CanvasService(final List<AbstractPostProcessingService> postProcessingServices) {
        this.postProcessingServices = postProcessingServices;
        this.effectContextList = new ArrayList<>();
    }

    @Override
    public void initialize() {
        this.canvasVertexArray = createVertexArray();
    }

    @Override
    public Canvas supply(final CanvasKey key) {
        List<PostProcessingEffectContext> effectContexts = createEffects(key);
        effectContextList.addAll(effectContexts);

        return new Canvas(key.parent, canvasVertexArray, effectContexts, key.framebuffer, key.color);
    }

    @Override
    public void terminate() {
        canvasVertexArray.clear();
        effectContextList.forEach(PostProcessingEffectContext::clear);
    }

    private VertexArray createVertexArray() {
        VertexArray vertexArray = new VertexArray(VERTICES.length / 2);

        return vertexArray
                .bind()
                .attachVertexBuffer(VERTEX, VERTICES, 2)
                .unbind();
    }

    private List<PostProcessingEffectContext> createEffects(final CanvasKey key) {
        List<PostProcessingEffectContext> effectContexts = new ArrayList<>();
        for (EffectType effectType : key.effects) {
            effectContexts.add(createEffectContext(effectType, key.framebuffer));
        }

        return effectContexts;
    }

    private PostProcessingEffectContext createEffectContext(final EffectType effectType, final Framebuffer framebuffer) {
        return findPostProcessingService(effectType).createContext(new Vector2i(framebuffer.width, framebuffer.height));
    }

    private AbstractPostProcessingService findPostProcessingService(final EffectType effectType) {
        return postProcessingServices.stream()
                .filter(pps -> pps.getEffectType().equals(effectType))
                .findFirst()
                .orElseThrow(() -> new PIEngineException("Could not find post processing service for type: %s!", effectType));
    }
}
