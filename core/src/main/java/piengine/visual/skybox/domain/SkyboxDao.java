package piengine.visual.skybox.domain;

import piengine.object.mesh.domain.MeshDao;

import java.util.List;

public class SkyboxDao extends MeshDao {

    public SkyboxDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }
}
