package piengine.object.particlesystem.manager;

import piengine.object.entity.domain.Entity;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.domain.ParticleSystemKey;
import piengine.object.particlesystem.service.ParticleSystemService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ParticleSystemManager {

    private final ParticleSystemService particleSystemService;

    @Wire
    public ParticleSystemManager(final ParticleSystemService particleSystemService) {
        this.particleSystemService = particleSystemService;
    }

    public ParticleSystem supply(final Entity parent, final float pps, final float speed,
                                 final float gravityComplient, final float lifeLength) {
        return particleSystemService.supply(new ParticleSystemKey(parent, pps, speed, gravityComplient, lifeLength));
    }
}
