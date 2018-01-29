package piengine.object.particlesystem.service;

import piengine.core.base.api.Service;
import piengine.core.base.api.Updatable;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.manager.MeshManager;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.domain.ParticleSystemKey;
import piengine.visual.sprite.domain.Sprite;
import piengine.visual.sprite.domain.SpriteKey;
import piengine.visual.sprite.service.SpriteService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParticleSystemService implements Service, Updatable {

    private final MeshManager meshManager;
    private final SpriteService spriteService;
    private final List<ParticleSystem> particleSystems;

    @Wire
    public ParticleSystemService(final MeshManager meshManager, final SpriteService spriteService) {
        this.meshManager = meshManager;
        this.spriteService = spriteService;
        this.particleSystems = new ArrayList<>();
    }

    public ParticleSystem supply(final ParticleSystemKey key) {
        Mesh mesh = meshManager.supply("canvas");
        Sprite sprite = spriteService.supply(new SpriteKey(key.sprite, key.spriteSize));

        ParticleSystem particleSystem = new ParticleSystem(key.parent, mesh, sprite, key.camera, key.pps, key.speed, key.gravityComplient, key.lifeLength);
        particleSystems.add(particleSystem);
        return particleSystem;
    }

    @Override
    public void update(final float delta) {
        for (ParticleSystem particleSystem : particleSystems) {
            particleSystem.update(delta);
        }
    }
}
