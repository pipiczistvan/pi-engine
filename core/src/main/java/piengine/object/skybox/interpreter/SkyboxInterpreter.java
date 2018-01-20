package piengine.object.skybox.interpreter;

import org.lwjgl.opengl.GL15;
import piengine.core.base.api.Interpreter;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.skybox.domain.SkyboxDao;
import piengine.object.skybox.domain.SkyboxData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.util.ArrayList;

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
import static piengine.core.utils.BufferUtils.convertToFloatBuffer;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

@Component
public class SkyboxInterpreter implements Interpreter<SkyboxData, SkyboxDao> {

    @Override
    public SkyboxDao create(final SkyboxData skyboxData) {
        final SkyboxDao dao = new SkyboxDao(glGenVertexArrays(), new ArrayList<>(), skyboxData.vertices.length / 3);
        bind(dao);

        dao.vboIds.add(createVbo(VERTEX.value, skyboxData.vertices, 3));

        unbind();

        return dao;
    }

    @Override
    public void free(final SkyboxDao dao) {
        glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);
    }

    private void bind(final MeshDao dao) {
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
}
