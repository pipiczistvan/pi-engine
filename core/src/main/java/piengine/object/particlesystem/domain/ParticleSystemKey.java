package piengine.object.particlesystem.domain;

import piengine.core.base.domain.Key;
import piengine.object.entity.domain.Entity;

public class ParticleSystemKey implements Key {

    public final Entity parent;
    public final float pps;
    public final float speed;
    public final float gravityComplient;
    public final float lifeLength;

    public ParticleSystemKey(final Entity parent, final float pps, final float speed,
                             final float gravityComplient, final float lifeLength) {
        this.parent = parent;
        this.pps = pps;
        this.speed = speed;
        this.gravityComplient = gravityComplient;
        this.lifeLength = lifeLength;
    }
}
