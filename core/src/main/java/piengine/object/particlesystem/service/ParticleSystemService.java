package piengine.object.particlesystem.service;

import piengine.core.base.api.Updatable;
import piengine.core.base.resource.EntitySupplierService;
import piengine.object.particlesystem.accessor.ParticleSystemAccessor;
import piengine.object.particlesystem.domain.ParticleSysemDao;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.domain.ParticleSystemData;
import piengine.object.particlesystem.domain.ParticleSystemKey;
import piengine.object.particlesystem.interpreter.ParticleSystemInterpreter;
import piengine.visual.sprite.domain.Sprite;
import piengine.visual.sprite.domain.SpriteKey;
import piengine.visual.sprite.service.SpriteService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ParticleSystemService extends EntitySupplierService<ParticleSystemKey, ParticleSystemData, ParticleSysemDao, ParticleSystem> implements Updatable {

    private final SpriteService spriteService;

    @Wire
    public ParticleSystemService(final ParticleSystemAccessor particleSystemAccessor, final ParticleSystemInterpreter particleSystemInterpreter,
                                 final SpriteService spriteService) {
        super(particleSystemAccessor, particleSystemInterpreter);

        this.spriteService = spriteService;
    }

    @Override
    protected ParticleSystem createDomain(final ParticleSysemDao dao, final ParticleSystemData resource) {
        Sprite sprite = spriteService.supply(new SpriteKey(resource.key.sprite, resource.key.spriteSize));

        return new ParticleSystem(resource.key.parent, dao, sprite, resource.key.camera, resource.key.pps, resource.key.speed, resource.key.gravityComplient, resource.key.lifeLength);
    }

    @Override
    public void update(final float delta) {
        for (ParticleSystem particleSystem : getDomainValues()) {
            particleSystem.update(delta);
        }
    }
}
