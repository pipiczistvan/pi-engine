package piengine.object.terrain.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.interpreter.MeshInterpreter;
import piengine.object.terrain.domain.TerrainDao;
import piengine.object.terrain.domain.TerrainData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TerrainInterpreter implements Interpreter<TerrainDao, TerrainData> {

    private final MeshInterpreter meshInterpreter;

    @Wire
    public TerrainInterpreter(final MeshInterpreter meshInterpreter) {
        this.meshInterpreter = meshInterpreter;
    }

    @Override
    public TerrainDao create(final TerrainData terrainData) {
        MeshDao meshDao = meshInterpreter.create(terrainData);

        return new TerrainDao(meshDao.vaoId, meshDao.vboIds, meshDao.vertexCount);
    }

    @Override
    public void free(final TerrainDao dao) {
        meshInterpreter.free(dao);
    }
}
