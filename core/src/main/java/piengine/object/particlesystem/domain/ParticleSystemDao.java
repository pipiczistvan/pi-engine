package piengine.object.particlesystem.domain;

import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;

import java.util.List;

import static piengine.object.mesh.domain.MeshDataType.BLEND;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_1;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_2;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_3;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_4;
import static piengine.object.mesh.domain.MeshDataType.TEXTURE_OFFSET;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

public class ParticleSystemDao extends MeshDao {

    private static MeshDataType[] MESH_DATA_TYPES = {
            VERTEX,
            MODEL_VIEW_MATRIX_1,
            MODEL_VIEW_MATRIX_2,
            MODEL_VIEW_MATRIX_3,
            MODEL_VIEW_MATRIX_4,
            TEXTURE_OFFSET,
            BLEND
    };

    public ParticleSystemDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }

    @Override
    public MeshDataType[] getVertexAttribs() {
        return MESH_DATA_TYPES;
    }
}
