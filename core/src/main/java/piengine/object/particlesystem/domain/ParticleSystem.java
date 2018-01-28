package piengine.object.particlesystem.domain;

import org.joml.Vector3f;
import piengine.core.base.api.Updatable;
import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem extends Entity implements Updatable {

    public final Mesh mesh;
    private final float pps;
    private final float speed;
    private final float gravityComplient;
    private final float lifeLength;
    public final List<Particle> particles;

    public ParticleSystem(final Entity parent, final Mesh mesh, final float pps,
                          final float speed, final float gravityComplient, final float lifeLength) {
        super(parent);
        this.mesh = mesh;
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

        particles.add(new Particle(this, velocity, gravityComplient, lifeLength));
    }
}
