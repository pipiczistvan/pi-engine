package piengine.object.skybox.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.object.skybox.domain.SkyboxDao;
import piengine.object.skybox.domain.SkyboxData;
import puppeteer.annotation.premade.Component;

@Component
public class SkyboxInterpreter extends Interpreter<SkyboxData, SkyboxDao> {

    @Override
    protected SkyboxDao createDao(final SkyboxData skyboxData) {
        VertexArray vertexArray = new VertexArray(skyboxData.vertices.length / 3)
                .bind()
                .attachVertexBuffer(VertexAttribute.VERTEX, skyboxData.vertices, 3)
                .unbind();

        return new SkyboxDao(vertexArray);
    }

    @Override
    protected boolean freeDao(final SkyboxDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final SkyboxDao dao, final SkyboxData resource) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final SkyboxDao dao) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }
}
