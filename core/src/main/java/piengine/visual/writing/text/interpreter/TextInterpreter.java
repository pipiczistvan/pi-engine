package piengine.visual.writing.text.interpreter;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import piengine.core.base.api.Interpreter;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;
import piengine.visual.writing.text.domain.TextDao;
import piengine.visual.writing.text.domain.TextData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.util.ArrayList;

@Component
public class TextInterpreter extends Interpreter<TextData, TextDao> {

    @Override
    protected TextDao createDao(final TextData textData) {
        final TextDao dao = new TextDao(GL30.glGenVertexArrays(), new ArrayList<>(), textData.vertices.length / 2);
        bind(dao);

        dao.vboIds.add(createVbo(MeshDataType.VERTEX.value, textData.vertices, 2));
        dao.vboIds.add(createVbo(MeshDataType.TEXTURE_COORD.value, textData.textureCoords, 2));

        unbind();

        return dao;
    }

    public void update(final TextDao dao, final TextData textData) {
        free(dao);

        MeshDao newDao = create(textData);

        dao.vaoId = newDao.vaoId;
        dao.vboIds = newDao.vboIds;
        dao.vertexCount = newDao.vertexCount;
    }

    @Override
    protected boolean freeDao(final TextDao dao) {
        GL30.glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);

        return true;
    }

    @Override
    protected String getCreateInfo(final TextDao dao, final TextData resource) {
        return String.format("Vao: %s", dao.vaoId);
    }

    @Override
    protected String getFreeInfo(final TextDao dao) {
        return String.format("Vao: %s", dao.vaoId);
    }

    private void bind(final MeshDao dao) {
        GL30.glBindVertexArray(dao.vaoId);
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

    private int createVbo(int index, float[] data, int size) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = convertToFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    private FloatBuffer convertToFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data).flip();

        return buffer;
    }

    @Override
    protected boolean shouldLog() {
        return false;
    }
}
