package piengine.object.particlesystem.domain;

import org.joml.Vector3f;
import piengine.core.base.api.Updatable;
import piengine.object.camera.domain.Camera;
import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;
import piengine.visual.sprite.domain.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem extends EntityDomain<ParticleSystemDao> implements Updatable {

    public final Sprite sprite;
    public final List<Particle> particles;

    private final Camera camera;
    private final float pps;
    private final float speed;
    private final float gravityComplient;
    private final float lifeLength;

    public ParticleSystem(final Entity parent, final ParticleSystemDao dao, final Sprite sprite,
                          final Camera camera, final float pps, final float speed,
                          final float gravityComplient, final float lifeLength) {
        super(parent, dao);
        this.sprite = sprite;
        this.camera = camera;
        this.pps = pps;
        this.speed = speed;
        this.gravityComplient = gravityComplient;
        this.lifeLength = lifeLength;
        this.particles = new ArrayList<>();
    }

    @Override
    public void update(final float delta) {
        generateParticles(delta);

        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(delta);
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
        sortHighToLow(particles);
    }

    private void generateParticles(final float delta) {
        float particlesToCreate = pps * delta;
        int count = (int) Math.floor(particlesToCreate);
        float partialParticle = particlesToCreate % 1;
        for (int i = 0; i < count; i++) {
            emitParticle();
        }
        if (Math.random() < partialParticle) {
            emitParticle();
        }
    }

    private void emitParticle() {
        float dirX = (float) Math.random() * 2f - 1f;
        float dirZ = (float) Math.random() * 2f - 1f;
        Vector3f velocity = new Vector3f(dirX, 1, dirZ);
        velocity.normalize();
        velocity.mul(speed);

        particles.add(new Particle(this, camera, velocity, gravityComplient, lifeLength, sprite.getNumberOfRows()));
    }

    private static void sortHighToLow(final List<Particle> list) {
        for (int i = 1; i < list.size(); i++) {
            Particle item = list.get(i);
            if (item.getDistance() > list.get(i - 1).getDistance()) {
                sortUpHighToLow(list, i);
            }
        }
    }

    private static void sortUpHighToLow(final List<Particle> list, final int i) {
        Particle item = list.get(i);
        int attemptPos = i - 1;
        while (attemptPos != 0 && list.get(attemptPos - 1).getDistance() < item.getDistance()) {
            attemptPos--;
        }
        list.remove(i);
        list.add(attemptPos, item);
    }
}
