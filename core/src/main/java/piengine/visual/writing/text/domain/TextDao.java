package piengine.visual.writing.text.domain;

import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;

import java.util.List;

import static piengine.object.mesh.domain.MeshDataType.TEXTURE_COORD;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

public class TextDao extends MeshDao {

    private static final MeshDataType[] MESH_DATA_TYPES = {VERTEX, TEXTURE_COORD};

    public TextDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }

    @Override
    public MeshDataType[] getVertexAttribs() {
        return MESH_DATA_TYPES;
    }
}
