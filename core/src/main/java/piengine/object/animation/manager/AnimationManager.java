package piengine.object.animation.manager;

import piengine.object.animation.domain.Animation;
import piengine.object.animation.domain.AnimationKey;
import piengine.object.animation.service.AnimationService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimationManager {

    private final AnimationService animationService;

    @Wire
    public AnimationManager(final AnimationService animationService) {
        this.animationService = animationService;
    }

    public Animation supply(final String colladaFile) {
        return animationService.supply(new AnimationKey(colladaFile));
    }
}
