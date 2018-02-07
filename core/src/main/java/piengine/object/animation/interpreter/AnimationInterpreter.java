package piengine.object.animation.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.object.animation.domain.AnimationDao;
import piengine.object.animation.domain.AnimationData;
import puppeteer.annotation.premade.Component;

@Component
public class AnimationInterpreter extends Interpreter<AnimationData, AnimationDao> {

    @Override
    protected AnimationDao createDao(final AnimationData animationData) {
        return new AnimationDao();
    }
}
