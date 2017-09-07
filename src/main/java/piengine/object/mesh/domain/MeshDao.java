package piengine.object.mesh.domain;

import piengine.core.base.domain.Dao;

import java.util.List;

public class MeshDao implements Dao {

    public final int vaoId;
    public final List<Integer> vboIds;
    public final int vertexCount;

    public MeshDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        this.vaoId = vaoId;
        this.vboIds = vboIds;
        this.vertexCount = vertexCount;
    }

}
