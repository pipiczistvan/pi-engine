package piengine.object.particlesystem.domain;

import piengine.core.base.domain.EntityKey;
import piengine.object.camera.domain.Camera;
import piengine.object.entity.domain.Entity;

public class ParticleSystemKey extends EntityKey {

    public final Camera camera;
    public final float pps;
    public final float speed;
    public final float gravityComplient;
    public final float lifeLength;
    public final String sprite;
    public final int spriteSize;

    public ParticleSystemKey(final Entity parent, final Camera camera, final float pps,
                             final float speed, final float gravityComplient, final float lifeLength,
                             final String sprite, final int spriteSize) {
        super(parent);
        this.camera = camera;
        this.pps = pps;
        this.speed = speed;
        this.gravityComplient = gravityComplient;
        this.lifeLength = lifeLength;
        this.sprite = sprite;
        this.spriteSize = spriteSize;
    }
}
