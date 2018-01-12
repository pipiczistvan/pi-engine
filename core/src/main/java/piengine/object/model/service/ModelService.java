package piengine.object.model.service;

import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.service.MeshService;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelService {

    private final MeshService meshService;

    @Wire
    public ModelService(final MeshService meshService) {
        this.meshService = meshService;
    }

    public Model supply(final ModelKey key) {
        Mesh mesh = meshService.supply(key.file);
        return new Model(key.parent, mesh, key.texture, key.color);
    }
}
