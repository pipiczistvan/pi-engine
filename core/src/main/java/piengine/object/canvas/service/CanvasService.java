package piengine.object.canvas.service;

import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.service.MeshService;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.postprocessing.domain.PostProcessingEffect;
import piengine.visual.postprocessing.service.PostProcessingService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class CanvasService {

    private final MeshService meshService;
    private final PostProcessingService postProcessingService;

    @Wire
    public CanvasService(final MeshService meshService, final PostProcessingService postProcessingService) {
        this.meshService = meshService;
        this.postProcessingService = postProcessingService;
    }

    public Canvas supply(final CanvasKey key) {
        Mesh mesh = meshService.supply(new MeshKey("canvas"));
        List<PostProcessingEffect> effects = new ArrayList<>();
        for (EffectType effectType : key.effects) {
            effects.add(postProcessingService.supply(effectType, key.texture));
        }

        return new Canvas(key.parent, mesh, key.texture, key.color, effects);
    }
}
