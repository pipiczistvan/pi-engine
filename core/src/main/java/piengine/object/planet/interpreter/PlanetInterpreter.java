package piengine.object.planet.interpreter;

import org.lwjgl.opengl.GL15;
import piengine.core.base.api.Interpreter;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.planet.domain.PlanetData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.BufferUtils.createFloatBuffer;
import static org.lwjgl.BufferUtils.createIntBuffer;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

@Component
public class PlanetInterpreter implements Interpreter<MeshDao, PlanetData> {

    @Override
    public MeshDao create(final PlanetData planetData) {
        final MeshDao dao = new MeshDao(glGenVertexArrays(), new ArrayList<>(), planetData.indices.length);
        bind(dao);

        dao.vboIds.add(createIndicesVbo(planetData.indices));
        dao.vboIds.add(createVbo(VERTEX.value, planetData.vertices, 3));

        unbind();

        return dao;
    }

    @Override
    public void free(final MeshDao dao) {
        glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);
    }

    private void bind(final MeshDao dao) {
        glBindVertexArray(dao.vaoId);
    }

    private void unbind() {
        glBindVertexArray(0);
    }

    private int createIndicesVbo(int[] data) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = convertToIntBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        return vbo;
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

    private IntBuffer convertToIntBuffer(int[] data) {
        IntBuffer buffer = createIntBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }

}
