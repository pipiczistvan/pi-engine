package piengine.object.mesh.domain;

import piengine.core.base.domain.Dao;
import piengine.core.opengl.vertexarray.VertexArray;

public class MeshDao implements Dao {

    public VertexArray vertexArray;

    public MeshDao(final VertexArray vertexArray) {
        this.vertexArray = vertexArray;
    }
}
