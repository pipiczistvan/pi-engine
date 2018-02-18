package piengine.object.mesh.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshData;
import puppeteer.annotation.premade.Component;

@Component
public class MeshInterpreter extends Interpreter<MeshData, MeshDao> {

    @Override
    protected MeshDao createDao(final MeshData meshData) {
        VertexArray vertexArray = new VertexArray(meshData.indices.length)
                .bind()
                .attachIndexBuffer(meshData.indices)
                .attachVertexBuffer(VertexAttribute.VERTEX, meshData.vertices, 3)
                .attachVertexBuffer(VertexAttribute.TEXTURE_COORD, meshData.textureCoords, 2)
                .attachVertexBuffer(VertexAttribute.NORMAL, meshData.normals, 3)
                .unbind();

        return new MeshDao(vertexArray);
    }

    @Override
    protected boolean freeDao(final MeshDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final MeshDao dao, final MeshData resource) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final MeshDao dao) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }
}
