package piengine.object.model.service;

import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.manager.MeshManager;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelService {

    private final MeshManager meshManager;

    @Wire
    public ModelService(final MeshManager meshManager) {
        this.meshManager = meshManager;
    }

    public Model supply(final ModelKey key) {
        Mesh mesh = meshManager.supply(key.file);
        return new Model(key.parent, mesh, key.texture, key.color);
    }
}
