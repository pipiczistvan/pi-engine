package piengine.object.model.service;

import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshKey;
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
        //todo: biztos így?
        Mesh mesh = meshService.supply(new MeshKey(key.file));
        return new Model(key.parent, mesh, key.texture, key.color);
    }
}
