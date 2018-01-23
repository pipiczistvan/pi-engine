package piengine.object.animatedmodel.interpreter;

import org.lwjgl.opengl.GL15;
import piengine.core.base.api.Interpreter;
import piengine.object.animatedmodel.domain.AnimatedModelDao;
import piengine.object.animatedmodel.domain.AnimatedModelData;
import piengine.object.mesh.domain.MeshDao;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
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
import static org.lwjgl.opengl.GL30.glVertexAttribIPointer;
import static piengine.core.utils.BufferUtils.convertToFloatBuffer;
import static piengine.core.utils.BufferUtils.convertToIntBuffer;
import static piengine.object.mesh.domain.MeshDataType.JOINT_INDEX;
import static piengine.object.mesh.domain.MeshDataType.NORMAL;
import static piengine.object.mesh.domain.MeshDataType.TEXTURE_COORD;
import static piengine.object.mesh.domain.MeshDataType.VERTEX;
import static piengine.object.mesh.domain.MeshDataType.WEIGHT;

@Component
public class AnimatedModelInterpreter implements Interpreter<AnimatedModelData, AnimatedModelDao> {

    @Override
    public AnimatedModelDao create(final AnimatedModelData animatedModelData) {
        final AnimatedModelDao dao = new AnimatedModelDao(glGenVertexArrays(), new ArrayList<>(), animatedModelData.mesh.indices.length);
        bind(dao);

        dao.vboIds.add(createIndicesVbo(animatedModelData.mesh.indices));
        dao.vboIds.add(createVbo(VERTEX.value, animatedModelData.mesh.vertices, 3));
        dao.vboIds.add(createVbo(TEXTURE_COORD.value, animatedModelData.mesh.textureCoords, 2));
        dao.vboIds.add(createVbo(NORMAL.value, animatedModelData.mesh.normals, 3));
        dao.vboIds.add(createVbo(JOINT_INDEX.value, animatedModelData.mesh.jointIds, 3));
        dao.vboIds.add(createVbo(WEIGHT.value, animatedModelData.mesh.vertexWeights, 3));

        unbind();

        return dao;
    }

    @Override
    public void free(final AnimatedModelDao dao) {
        glDeleteVertexArrays(dao.vaoId);
        dao.vboIds.forEach(GL15::glDeleteBuffers);
    }

    private void bind(final MeshDao dao) {
        glBindVertexArray(dao.vaoId);
    }

    private void unbind() {
        glBindVertexArray(0);
    }

    private int createIndicesVbo(final int[] data) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = convertToIntBuffer(data);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

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

    private int createVbo(final int index, final int[] data, final int size) {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        IntBuffer buffer = convertToIntBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribIPointer(index, size, GL_INT, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo;
    }
}
