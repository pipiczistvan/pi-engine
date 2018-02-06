package piengine.object.animation.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.object.animation.domain.Animation;
import piengine.object.animation.service.AnimationService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimationManager extends SupplierManager<String, Animation> {

    @Wire
    public AnimationManager(final AnimationService animationService) {
        super(animationService);
    }
}
