package piengine.object.particlesystem.domain;

import org.joml.Vector3f;
import piengine.core.base.api.Updatable;
import piengine.object.entity.domain.Entity;

public class Particle extends Entity implements Updatable {

    private static final float GRAVITY = -100;

    private final Vector3f velocity;
    private final float gravityEffect;
    private final float lifeLength;

    private float elapsedTime = 0;

    public Particle(final Entity parent, final Vector3f velocity, final float gravityEffect, final float lifeLength) {
        super(parent);
        this.velocity = velocity;
        this.gravityEffect = gravityEffect;
        this.lifeLength = lifeLength;
    }

    @Override
    public void update(final float delta) {
        velocity.y += GRAVITY * gravityEffect * delta;
        Vector3f change = new Vector3f(velocity);
        change.mul(delta);
        translate(change);
        elapsedTime += delta;
    }

    public boolean isAlive() {
        return elapsedTime < lifeLength;
    }
}
