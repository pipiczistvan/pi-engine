package piengine.object.terrain.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.object.terrain.domain.TerrainDao;
import piengine.object.terrain.domain.TerrainData;
import puppeteer.annotation.premade.Component;

@Component
public class TerrainInterpreter extends Interpreter<TerrainData, TerrainDao> {

    @Override
    protected TerrainDao createDao(final TerrainData terrainData) {
        VertexArray vertexArray = new VertexArray(terrainData.indices.length)
                .bind()
                .attachIndexBuffer(terrainData.indices)
                .attachVertexBuffer(VertexAttribute.VERTEX, terrainData.vertices, 3)
                .attachVertexBuffer(VertexAttribute.COLOR, terrainData.colors, 3)
                .attachVertexBuffer(VertexAttribute.NORMAL, terrainData.normals, 3)
                .unbind();

        return new TerrainDao(vertexArray);
    }

    @Override
    protected boolean freeDao(final TerrainDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final TerrainDao dao, final TerrainData resource) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final TerrainDao dao) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }
}
