package piengine.object.water.domain;

import piengine.object.mesh.domain.MeshDao;

import java.util.List;

public class WaterDao extends MeshDao {

    public WaterDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }
}
