package piengine.object.mesh.domain;

import piengine.core.base.domain.Dao;

import java.util.List;

import static piengine.object.mesh.domain.MeshDataType.NORMAL;
import static piengine.object.mesh.domain.MeshDataType.TEXTURE_COORD;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

public class MeshDao implements Dao {

    private static MeshDataType[] MESH_DATA_TYPES = {VERTEX, TEXTURE_COORD, NORMAL};

    public int vaoId;
    public List<Integer> vboIds;
    public int vertexCount;

    public MeshDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        this.vaoId = vaoId;
        this.vboIds = vboIds;
        this.vertexCount = vertexCount;
    }

    public MeshDataType[] getVertexAttribs() {
        return MESH_DATA_TYPES;
    }
}
