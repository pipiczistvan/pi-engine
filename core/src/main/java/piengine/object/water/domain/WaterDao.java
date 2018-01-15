package piengine.object.water.domain;

import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;

import java.util.List;

import static piengine.object.mesh.domain.MeshDataType.COLOR;
import static piengine.object.mesh.domain.MeshDataType.INDICATOR;
import static piengine.object.mesh.domain.MeshDataType.NORMAL;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

public class WaterDao extends MeshDao {

    private static MeshDataType[] meshDataTypes = {VERTEX, NORMAL, COLOR, INDICATOR};

    public WaterDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }

    @Override
    public MeshDataType[] getVertexAttribs() {
        return meshDataTypes;
    }
}
