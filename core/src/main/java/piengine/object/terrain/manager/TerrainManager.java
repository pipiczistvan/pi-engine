package piengine.object.terrain.manager;

import piengine.object.entity.domain.Entity;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.domain.TerrainKey;
import piengine.object.terrain.service.TerrainService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TerrainManager {

    private final TerrainService terrainService;

    @Wire
    public TerrainManager(final TerrainService terrainService) {
        this.terrainService = terrainService;
    }

    public Terrain supply(final Entity parent, final String heightmap) {
        return terrainService.supply(new TerrainKey(parent, heightmap));
    }
}
