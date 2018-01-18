package piengine.visual.pointshadow.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.pointshadow.domain.PointShadowDao;
import piengine.visual.pointshadow.domain.PointShadowData;
import puppeteer.annotation.premade.Component;

@Component
public class PointShadowInterpreter implements Interpreter<PointShadowData, PointShadowDao> {

    @Override
    public PointShadowDao create(final PointShadowData pointShadowData) {
        return new PointShadowDao();
    }
}
