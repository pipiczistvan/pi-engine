package piengine.object.animatedmodel.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.object.animatedmodel.domain.AnimatedModelDao;
import piengine.object.animatedmodel.domain.AnimatedModelData;
import puppeteer.annotation.premade.Component;

@Component
public class AnimatedModelInterpreter extends Interpreter<AnimatedModelData, AnimatedModelDao> {

    @Override
    protected AnimatedModelDao createDao(final AnimatedModelData animatedModelData) {
        VertexArray vertexArray = new VertexArray(animatedModelData.mesh.indices.length)
                .bind()
                .attachIndexBuffer(animatedModelData.mesh.indices)
                .attachVertexBuffer(VertexAttribute.VERTEX, animatedModelData.mesh.vertices, 3)
                .attachVertexBuffer(VertexAttribute.TEXTURE_COORD, animatedModelData.mesh.textureCoords, 2)
                .attachVertexBuffer(VertexAttribute.NORMAL, animatedModelData.mesh.normals, 3)
                .attachVertexBuffer(VertexAttribute.JOINT_INDEX, animatedModelData.mesh.jointIds, 3)
                .attachVertexBuffer(VertexAttribute.WEIGHT, animatedModelData.mesh.vertexWeights, 3)
                .unbind();

        return new AnimatedModelDao(vertexArray);
    }

    @Override
    protected boolean freeDao(final AnimatedModelDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final AnimatedModelDao dao, final AnimatedModelData resource) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final AnimatedModelDao dao) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }
}
