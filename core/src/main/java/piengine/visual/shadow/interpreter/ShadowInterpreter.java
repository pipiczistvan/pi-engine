package piengine.visual.shadow.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.shadow.domain.ShadowDao;
import piengine.visual.shadow.domain.ShadowData;
import puppeteer.annotation.premade.Component;

@Component
public class ShadowInterpreter implements Interpreter<ShadowData, ShadowDao> {

    @Override
    public ShadowDao create(final ShadowData shadowData) {
        return new ShadowDao();
    }
}
