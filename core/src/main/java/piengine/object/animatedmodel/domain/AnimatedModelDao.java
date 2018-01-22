package piengine.object.animatedmodel.domain;

import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;

import java.util.List;

import static piengine.object.mesh.domain.MeshDataType.JOINT_INDEX;
import static piengine.object.mesh.domain.MeshDataType.NORMAL;
import static piengine.object.mesh.domain.MeshDataType.TEXTURE_COORD;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;
import static piengine.object.mesh.domain.MeshDataType.WEIGHT;

public class AnimatedModelDao extends MeshDao {

    private static final MeshDataType[] MESH_DATA_TYPES = {VERTEX, NORMAL, TEXTURE_COORD, JOINT_INDEX, WEIGHT};

    public AnimatedModelDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }

    @Override
    public MeshDataType[] getVertexAttribs() {
        return MESH_DATA_TYPES;
    }
}
