package piengine.planet.asset;

import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.planet.manager.PlanetManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class PlanetAsset extends Asset {

    private final PlanetManager planetManager;

    private Model planet;

    @Wire
    public PlanetAsset(final RenderManager renderManager, final PlanetManager planetManager) {
        super(renderManager);
        this.planetManager = planetManager;
    }

    @Override
    public void initialize() {
        this.planet = planetManager.supply("cube", this);
        this.planet.setRotation(0, 0, 70);
    }

    @Override
    public void update(double delta) {
        planet.addRotation(0, 30f * (float) delta, 0);
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withPlanet(planet);
    }

}
