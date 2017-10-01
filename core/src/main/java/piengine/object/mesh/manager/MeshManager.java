package piengine.object.mesh.manager;

import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.service.MeshService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class MeshManager {

    private final MeshService meshService;

    @Wire
    public MeshManager(final MeshService meshService) {
        this.meshService = meshService;
    }

    public Mesh supply(final String file) {
        return meshService.supply(file);
    }

}
