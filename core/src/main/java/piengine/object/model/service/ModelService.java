package piengine.object.model.service;

import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.service.MeshService;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelAttribute;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelService {

    private final MeshService meshService;

    @Wire
    public ModelService(final MeshService meshService) {
        this.meshService = meshService;
    }

    public Model supply(final String file, final Entity parent, final ModelAttribute attribute) {
        Mesh mesh = meshService.supply(file);
        return new Model(mesh, attribute, parent);
    }
}
