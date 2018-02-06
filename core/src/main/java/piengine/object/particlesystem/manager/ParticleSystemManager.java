package piengine.object.particlesystem.manager;

import piengine.core.architecture.manager.SupplierManager;
import piengine.core.base.domain.Entity;
import piengine.object.camera.domain.Camera;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.domain.ParticleSystemKey;
import piengine.object.particlesystem.service.ParticleSystemService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ParticleSystemManager extends SupplierManager<ParticleSystemKey, ParticleSystem> {

    @Wire
    public ParticleSystemManager(final ParticleSystemService particleSystemService) {
        super(particleSystemService);
    }

    public ParticleSystem supply(final Entity parent, final Camera camera, final float pps,
                                 final float speed, final float gravityComplient, final float lifeLength,
                                 final String sprite, final int spriteSize) {
        return supply(new ParticleSystemKey(parent, camera, pps, speed, gravityComplient, lifeLength, sprite, spriteSize));
    }
}
