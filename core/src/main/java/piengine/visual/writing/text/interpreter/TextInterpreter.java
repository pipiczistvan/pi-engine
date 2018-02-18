package piengine.visual.writing.text.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.object.mesh.domain.MeshDao;
import piengine.visual.writing.text.domain.TextDao;
import piengine.visual.writing.text.domain.TextData;
import puppeteer.annotation.premade.Component;

@Component
public class TextInterpreter extends Interpreter<TextData, TextDao> {

    @Override
    protected TextDao createDao(final TextData textData) {
        VertexArray vertexArray = new VertexArray(textData.vertices.length / 2)
                .bind()
                .attachVertexBuffer(VertexAttribute.VERTEX, textData.vertices, 2)
                .attachVertexBuffer(VertexAttribute.TEXTURE_COORD, textData.textureCoords, 2)
                .unbind();

        return new TextDao(vertexArray);
    }

    public void update(final TextDao dao, final TextData textData) {
        free(dao);
        MeshDao newDao = create(textData);

        dao.vertexArray = newDao.vertexArray;
    }

    @Override
    protected boolean freeDao(final TextDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final TextDao dao, final TextData resource) {
        return String.format("Vao: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final TextDao dao) {
        return String.format("Vao: %s", dao.vertexArray.id);
    }

    @Override
    protected boolean shouldLog() {
        return false;
    }
}
