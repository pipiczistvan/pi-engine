package piengine.visual.lighting.directional.shadow.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowDao;
import piengine.visual.lighting.directional.shadow.domain.DirectionalShadowData;
import puppeteer.annotation.premade.Component;

@Component
public class DirectionalShadowInterpreter implements Interpreter<DirectionalShadowData, DirectionalShadowDao> {

    @Override
    public DirectionalShadowDao create(final DirectionalShadowData directionalShadowData) {
        return new DirectionalShadowDao();
    }
}
