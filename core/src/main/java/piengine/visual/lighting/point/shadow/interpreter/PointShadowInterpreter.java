package piengine.visual.lighting.point.shadow.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.lighting.point.shadow.domain.PointShadowDao;
import piengine.visual.lighting.point.shadow.domain.PointShadowData;
import puppeteer.annotation.premade.Component;

@Component
public class PointShadowInterpreter extends Interpreter<PointShadowData, PointShadowDao> {

    @Override
    protected PointShadowDao createDao(final PointShadowData pointShadowData) {
        return new PointShadowDao();
    }
}
