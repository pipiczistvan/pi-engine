package piengine.object.particlesystem.interpreter;

import org.lwjgl.opengl.GL15;
import piengine.core.base.api.Interpreter;
import piengine.object.particlesystem.domain.ParticleSysemDao;
import piengine.object.particlesystem.domain.ParticleSystemData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;
import static piengine.core.utils.BufferUtils.convertToFloatBuffer;
import static piengine.object.mesh.domain.MeshDataType.BLEND;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_1;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_2;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_3;
import static piengine.object.mesh.domain.MeshDataType.MODEL_VIEW_MATRIX_4;
import static piengine.object.mesh.domain.MeshDataType.TEXTURE_OFFSET;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;

@Component
public class ParticleSystemInterpreter extends Interpreter<ParticleSystemData, ParticleSysemDao> {

    public static final int MAX_INSTANCES = 10_000;
    public static final int INSTANCE_DATA_LENGTH = 21;
    private static final int FLOAT_SIZE = 4;

    @Override
    protected ParticleSysemDao createDao(final ParticleSystemData particleSystemData) {
        final ParticleSysemDao dao = new ParticleSysemDao(glGenVertexArrays(), new ArrayList<>(), particleSystemData.vertices.length / 2);
        bind(dao);

        dao.vboIds.add(createVbo(VERTEX.value, particleSystemData.vertices, 2));

        int vbo = createEmptyVbo(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
        dao.vboIds.add(vbo);
        addInstancedAttribute(vbo, MODEL_VIEW_MATRIX_1.value, 4, INSTANCE_DATA_LENGTH, 0);
        addInstancedAttribute(vbo, MODEL_VIEW_MATRIX_2.value, 4, INSTANCE_DATA_LENGTH, 4);
        addInstancedAttribute(vbo, MODEL_VIEW_MATRIX_3.value, 4, INSTANCE_DATA_LENGTH, 8);
        addInstancedAttribute(vbo, MODEL_VIEW_MATRIX_4.value, 4, INSTANCE_DATA_LENGTH, 12);
        addInstancedAttribute(vbo, TEXTURE_OFFSET.value, 4, INSTANCE_DATA_LENGTH, 16);
        addInstancedAttribute(vbo, BLEND.value, 1, INSTANCE_DATA_LENGTH, 20);

        unbind();

        return dao;
    }

    @Override
    protected boolean freeDao(final ParticleSysemDao dao) {
        glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);

        return true;
    }

    @Override
    protected String getCreateInfo(final ParticleSysemDao dao, final ParticleSystemData resource) {
        return String.format("Vao id: %s", dao.vaoId);
    }

    @Override
    protected String getFreeInfo(final ParticleSysemDao dao) {
        return String.format("Vao id: %s", dao.vaoId);
    }

    public void updateVbo(final ParticleSysemDao dao, final float[] data, final FloatBuffer buffer) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, dao.vboIds.get(1)); //todo: Hack
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * FLOAT_SIZE, GL_STREAM_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void bind(final ParticleSysemDao dao) {
        glBindVertexArray(dao.vaoId);
    }

    private void unbind() {
        glBindVertexArray(0);
    }

    private int createEmptyVbo(final int floatCount) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, floatCount * 4, GL_STREAM_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    private int createVbo(final int index, final float[] data, final int size) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = convertToFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }

    private void addInstancedAttribute(final int vbo, final int index, final int dataSize, final int instancedDataLength, final int offset) {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(index, dataSize, GL_FLOAT, false, instancedDataLength * FLOAT_SIZE, offset * FLOAT_SIZE);
        glVertexAttribDivisor(index, 1);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
