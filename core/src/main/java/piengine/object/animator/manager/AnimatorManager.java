package piengine.object.animator.manager;

import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animator.domain.Animator;
import piengine.object.animator.service.AnimatorService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatorManager {

    private final AnimatorService animatorService;

    @Wire
    public AnimatorManager(final AnimatorService animatorService) {
        this.animatorService = animatorService;
    }

    public Animator supply(final AnimatedModel animatedModel) {
        return animatorService.supply(animatedModel);
    }
}
