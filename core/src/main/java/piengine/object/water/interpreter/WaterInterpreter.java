package piengine.object.water.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.core.opengl.vertexarray.VertexArray;
import piengine.core.opengl.vertexarray.VertexAttribute;
import piengine.object.water.domain.WaterDao;
import piengine.object.water.domain.WaterData;
import puppeteer.annotation.premade.Component;

@Component
public class WaterInterpreter extends Interpreter<WaterData, WaterDao> {

    @Override
    protected WaterDao createDao(final WaterData waterData) {
        VertexArray vertexArray = new VertexArray(waterData.vertices.length / 3)
                .bind()
                .attachVertexBuffer(VertexAttribute.VERTEX, waterData.vertices, 3)
                .attachVertexBuffer(VertexAttribute.INDICATOR, waterData.indicators, 4)
                .unbind();

        return new WaterDao(vertexArray);
    }

    @Override
    protected boolean freeDao(final WaterDao dao) {
        dao.vertexArray.clear();

        return true;
    }

    @Override
    protected String getCreateInfo(final WaterDao dao, final WaterData resource) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }

    @Override
    protected String getFreeInfo(final WaterDao dao) {
        return String.format("Vao id: %s", dao.vertexArray.id);
    }
}
