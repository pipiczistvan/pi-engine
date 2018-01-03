package piengine.visual.writing.text.domain;

import piengine.object.mesh.domain.MeshDao;

import java.util.List;

public class TextDao extends MeshDao {

    public TextDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        super(vaoId, vboIds, vertexCount);
    }
}
