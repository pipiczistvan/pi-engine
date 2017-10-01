package piengine.planet.interpreter;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import piengine.core.base.api.Interpreter;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshDataType;
import piengine.planet.domain.PlanetData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

@Component
public class PlanetInterpreter implements Interpreter<MeshDao, PlanetData> {

    @Override
    public MeshDao create(final PlanetData planetData) {
        final MeshDao dao = new MeshDao(GL30.glGenVertexArrays(), new ArrayList<>(), planetData.indices.length);
        bind(dao);

        dao.vboIds.add(createIndicesVbo(planetData.indices));
        dao.vboIds.add(createVbo(MeshDataType.VERTEX.value, planetData.vertices, 3));

        unbind();

        return dao;
    }

    @Override
    public void free(final MeshDao dao) {
        GL30.glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);
    }

    private void bind(final MeshDao dao) {
        GL30.glBindVertexArray(dao.vaoId);
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

    private int createIndicesVbo(int[] data) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = convertToIntBuffer(data);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        return vbo;
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

    private IntBuffer convertToIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }

}
