package piengine.object.model.service;

import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.service.MeshService;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.TexturedModel;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelService {

    private final MeshService meshService;

    @Wire
    public ModelService(final MeshService meshService) {
        this.meshService = meshService;
    }

    public Model supply(final String file, final Entity parent) {
        Mesh mesh = meshService.supply(file);
        return new Model(mesh, parent);
    }

    public TexturedModel supply(final String file, final Entity parent, final Texture texture) {
        Mesh mesh = meshService.supply(file);
        return new TexturedModel(mesh, parent, texture);
    }
}
