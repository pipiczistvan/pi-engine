package piengine.object.canvas.service;

import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.domain.CanvasKey;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.service.MeshService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class CanvasService {

    private final MeshService meshService;

    @Wire
    public CanvasService(final MeshService meshService) {
        this.meshService = meshService;
    }

    public Canvas supply(final CanvasKey key) {
        Mesh mesh = meshService.supply(new MeshKey("canvas"));
        return new Canvas(key.parent, mesh, key.texture, key.color);
    }
}
