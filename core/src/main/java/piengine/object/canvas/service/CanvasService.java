package piengine.object.canvas.service;

import piengine.core.base.exception.PIEngineException;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.service.MeshService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.postprocessing.service.AbstractPostProcessingService;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class CanvasService {

    private final MeshService meshService;
    private final List<AbstractPostProcessingService> postProcessingServices;

    @Wire
    public CanvasService(final MeshService meshService, final List<AbstractPostProcessingService> postProcessingServices) {
        this.meshService = meshService;
        this.postProcessingServices = postProcessingServices;
    }

    public Canvas supply(final CanvasKey key) {
        Mesh mesh = meshService.supply(new MeshKey("canvas"));

        List<PostProcessingEffectContext> effectContexts = new ArrayList<>();
        for (EffectType effectType : key.effects) {
            effectContexts.add(createEffectContext(effectType, key.texture));
        }

        return new Canvas(key.parent, mesh, effectContexts, key.texture, key.color);
    }

    private PostProcessingEffectContext createEffectContext(final EffectType effectType, final Texture texture) {
        return postProcessingServices.stream()
                .filter(pps -> pps.getEffectType().equals(effectType))
                .findFirst()
                .orElseThrow(() -> new PIEngineException("Could not find post processing service for type: %s!", effectType))
                .createContext(texture, texture.getSize());
    }
}
