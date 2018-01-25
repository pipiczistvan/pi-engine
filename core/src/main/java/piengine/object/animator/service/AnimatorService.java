package piengine.object.animator.service;

import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animator.domain.Animator;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimatorService implements Service, Updatable {

    private final List<Animator> registeredAnimators;

    public AnimatorService() {
        this.registeredAnimators = new ArrayList<>();
    }

    public Animator supply(final AnimatedModel animatedModel) {
        Animator animator = new Animator(animatedModel);
        registeredAnimators.add(animator);

        return animator;
    }

    @Override
    public void update(final float delta) {
        for (Animator animator : registeredAnimators) {
            animator.update(delta);
        }
    }
}
