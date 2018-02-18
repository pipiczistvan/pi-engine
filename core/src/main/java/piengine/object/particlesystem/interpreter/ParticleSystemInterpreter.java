package piengine.object.particlesystem.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.core.opengl.vertexarray.VertexBuffer;
import piengine.object.particlesystem.domain.ParticleSystemDao;
import piengine.object.particlesystem.domain.ParticleSystemData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;

@Component
public class ParticleSystemInterpreter extends Interpreter<ParticleSystemData, ParticleSystemDao> {

    public static final int MAX_INSTANCES = 10_000;
    public static final int INSTANCE_DATA_LENGTH = 21;

    @Override
    protected ParticleSystemDao createDao(final ParticleSystemData particleSystemData) {
        VertexArray vertexArray = new VertexArray(particleSystemData.vertices.length / 2)
                .bind()
                .attachVertexBuffer(VertexAttribute.VERTEX, particleSystemData.vertices, 2)
                .attachVertexBuffer(VertexAttribute.EMPTY, new VertexBuffer()
                        .bind()
                        .attachFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH)
                        .attachAttribute(VertexAttribute.MODEL_VIEW_MATRIX_1, 4, INSTANCE_DATA_LENGTH, 0)
                        .attachAttribute(VertexAttribute.MODEL_VIEW_MATRIX_2, 4, INSTANCE_DATA_LENGTH, 4)
                        .attachAttribute(VertexAttribute.MODEL_VIEW_MATRIX_3, 4, INSTANCE_DATA_LENGTH, 8)
                        .attachAttribute(VertexAttribute.MODEL_VIEW_MATRIX_4, 4, INSTANCE_DATA_LENGTH, 12)
                        .attachAttribute(VertexAttribute.TEXTURE_OFFSET, 4, INSTANCE_DATA_LENGTH, 16)
                        .attachAttribute(VertexAttribute.BLEND, 1, INSTANCE_DATA_LENGTH, 20)
                        .unbind()
                )
                .unbind();

        return new ParticleSystemDao(vertexArray);
    }

    @Override
    protected boolean freeDao(final ParticleSystemDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final ParticleSystemDao dao, final ParticleSystemData resource) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final ParticleSystemDao dao) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    public void updateVbo(final ParticleSystemDao dao, final float[] data, final FloatBuffer buffer) {
        dao.vertexArray.vertexBuffers.get(VertexAttribute.EMPTY)
                .bind()
                .update(data, buffer)
                .unbind();
    }
}
