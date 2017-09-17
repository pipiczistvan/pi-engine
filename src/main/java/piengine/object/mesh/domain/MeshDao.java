package piengine.object.mesh.domain;

import piengine.core.base.domain.Dao;

import java.util.List;

public class MeshDao implements Dao {

    // todo: final
    public int vaoId;
    public List<Integer> vboIds;
    public int vertexCount;

    public MeshDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        this.vaoId = vaoId;
        this.vboIds = vboIds;
        this.vertexCount = vertexCount;
    }

}
