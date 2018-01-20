package piengine.visual.lighting.point.shadow.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.lighting.point.shadow.domain.PointShadowDao;
import piengine.visual.lighting.point.shadow.domain.PointShadowData;
import puppeteer.annotation.premade.Component;

@Component
public class PointShadowInterpreter implements Interpreter<PointShadowData, PointShadowDao> {

    @Override
    public PointShadowDao create(final PointShadowData pointShadowData) {
        return new PointShadowDao();
    }
}
