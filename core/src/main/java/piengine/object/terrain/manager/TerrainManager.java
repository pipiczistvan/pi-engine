package piengine.object.terrain.manager;

import org.joml.Vector3f;
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

    public Terrain supply(final Vector3f position, final Vector3f rotation, final Vector3f scale, final String heightmap) {
        return terrainService.supply(new TerrainKey(position, rotation, scale, heightmap));
    }

    public Terrain supply(final Vector3f position, final Vector3f scale, final String heightmap) {
        return supply(position, new Vector3f(0), scale, heightmap);
    }
}
