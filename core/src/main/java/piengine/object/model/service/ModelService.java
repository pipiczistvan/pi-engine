package piengine.object.model.service;

import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.service.MeshService;
import piengine.object.model.domain.Model;
import piengine.object.planet.domain.Planet;
import piengine.object.planet.service.PlanetService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ModelService {

    private final MeshService meshService;
    private final PlanetService planetService;

    @Wire
    public ModelService(final MeshService meshService,
                        final PlanetService planetService) {
        this.meshService = meshService;
        this.planetService = planetService;
    }

    public Model supply(final String file, final Entity parent) {
        Mesh mesh = meshService.supply(file);
        return new Model(mesh, parent);
    }

    public Planet supplyPlanet(final String file, final Entity parent) {
        Mesh mesh = planetService.supply(file);
        return new Planet(mesh, parent);
    }

}
