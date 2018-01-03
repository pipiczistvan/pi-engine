package piengine.object.terrain.domain;

import piengine.object.mesh.domain.MeshDao;

import java.util.List;

public class TerrainDao extends MeshDao {

    public TerrainDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }
}
