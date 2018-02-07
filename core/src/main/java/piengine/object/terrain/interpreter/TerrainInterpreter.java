package piengine.object.terrain.interpreter;

import org.lwjgl.opengl.GL15;
import piengine.core.base.api.Interpreter;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.terrain.domain.TerrainDao;
import piengine.object.terrain.domain.TerrainData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

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
import static piengine.core.utils.BufferUtils.convertToFloatBuffer;
import static piengine.core.utils.BufferUtils.convertToIntBuffer;
import static piengine.object.mesh.domain.MeshDataType.COLOR;
import static piengine.object.mesh.domain.MeshDataType.NORMAL;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

@Component
public class TerrainInterpreter extends Interpreter<TerrainData, TerrainDao> {

    @Override
    protected TerrainDao createDao(final TerrainData terrainData) {
        final TerrainDao dao = new TerrainDao(glGenVertexArrays(), new ArrayList<>(), terrainData.indices.length);
        bind(dao);

        dao.vboIds.add(createIndicesVbo(terrainData.indices));
        dao.vboIds.add(createVbo(VERTEX.value, terrainData.vertices, 3));
        dao.vboIds.add(createVbo(COLOR.value, terrainData.colors, 3));
        dao.vboIds.add(createVbo(NORMAL.value, terrainData.normals, 3));

        unbind();

        return dao;
    }

    @Override
    protected boolean freeDao(final TerrainDao dao) {
        glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);

        return true;
    }

    @Override
    protected String getCreateInfo(final TerrainDao dao, final TerrainData resource) {
        return String.format("Vao id: %s", dao.vaoId);
    }

    @Override
    protected String getFreeInfo(final TerrainDao dao) {
        return String.format("Vao id: %s", dao.vaoId);
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
}
