package piengine.visual.writing.text.interpreter;

import org.lwjgl.opengl.GL15;
import piengine.core.base.api.Interpreter;
import piengine.visual.writing.text.domain.TextDao;
import piengine.visual.writing.text.domain.TextData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static piengine.object.mesh.domain.MeshDataType.TEXTURE_COORD;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

@Component
public class TextInterpreter implements Interpreter<TextDao, TextData> {

    @Override
    public TextDao create(TextData textData) {
        final TextDao dao = new TextDao(glGenVertexArrays(), new ArrayList<>(), textData.vertices.length / 2);
        bind(dao);

        dao.vboIds.add(createVbo(VERTEX.value, textData.vertices, 2));
        dao.vboIds.add(createVbo(TEXTURE_COORD.value, textData.textureCoords, 2));

        unbind();

        return dao;
    }

    public void update(final TextDao dao, final TextData textData) {
        free(dao);

        TextDao newDao = create(textData);

        dao.vaoId = newDao.vaoId;
        dao.vboIds = newDao.vboIds;
        dao.vertexCount = newDao.vertexCount;
    }

    @Override
    public void free(final TextDao dao) {
        glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);
    }

    private void bind(final TextDao dao) {
        glBindVertexArray(dao.vaoId);
    }

    private void unbind() {
        glBindVertexArray(0);
    }

    private int createVbo(int index, float[] data, int size) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = convertToFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    private FloatBuffer convertToFloatBuffer(float[] data) {
        FloatBuffer buffer = createFloatBuffer(data.length);
        buffer.put(data).flip();

        return buffer;
    }

}
