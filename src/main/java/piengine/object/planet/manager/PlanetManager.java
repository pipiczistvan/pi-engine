package piengine.object.planet.manager;

import piengine.object.entity.domain.Entity;
import piengine.object.model.service.ModelService;
import piengine.object.planet.domain.Planet;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class PlanetManager {

    private final ModelService modelService;

    @Wire
    public PlanetManager(final ModelService modelService) {
        this.modelService = modelService;
    }

    public Planet supply(final String file, final Entity parent) {
        return modelService.supplyPlanet(file, parent);
    }

}
