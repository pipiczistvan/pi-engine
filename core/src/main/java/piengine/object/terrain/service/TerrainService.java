package piengine.object.terrain.service;

import piengine.core.base.resource.SupplierService;
import piengine.object.terrain.accessor.TerrainAccessor;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.domain.TerrainDao;
import piengine.object.terrain.domain.TerrainData;
import piengine.object.terrain.interpreter.TerrainInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TerrainService extends SupplierService<Terrain, TerrainDao, TerrainData> {

    @Wire
    public TerrainService(final TerrainAccessor terrainAccessor, final TerrainInterpreter terrainInterpreter) {
        super(terrainAccessor, terrainInterpreter);
    }

    @Override
    protected Terrain createDomain(final TerrainDao dao, final TerrainData resource) {
        return new Terrain(dao);
    }
}
