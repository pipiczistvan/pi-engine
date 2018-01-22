package piengine.object.skybox.domain;

import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;

import java.util.List;

import static piengine.object.mesh.domain.MeshDataType.VERTEX;

public class SkyboxDao extends MeshDao {

    private static final MeshDataType[] MESH_DATA_TYPES = {VERTEX};

    public SkyboxDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }

    @Override
    public MeshDataType[] getVertexAttribs() {
        return MESH_DATA_TYPES;
    }
}
