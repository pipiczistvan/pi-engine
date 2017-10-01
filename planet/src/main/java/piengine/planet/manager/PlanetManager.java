package piengine.planet.manager;

import piengine.common.planet.domain.Planet;
import piengine.object.entity.domain.Entity;
import piengine.planet.service.PlanetService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class PlanetManager {

    private PlanetService planetService;

    @Wire
    public PlanetManager(final PlanetService planetService) {
        this.planetService = planetService;
    }

    public Planet supply(final String file, final Entity parent) {
        return planetService.supply(file, parent);
    }

}
