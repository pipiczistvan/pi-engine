package piengine.object.particlesystem.service;

import piengine.core.architecture.service.SupplierService;
import piengine.core.base.api.Initializable;
import piengine.core.base.api.Updatable;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.domain.ParticleSystemKey;
import piengine.visual.texture.sprite.domain.Sprite;
import piengine.visual.texture.sprite.domain.SpriteKey;
import piengine.visual.texture.sprite.service.SpriteService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

import static piengine.io.interpreter.vertexarray.VertexAttribute.BLEND;
import static piengine.io.interpreter.vertexarray.VertexAttribute.EMPTY;
import static piengine.io.interpreter.vertexarray.VertexAttribute.MODEL_VIEW_MATRIX_1;
import static piengine.io.interpreter.vertexarray.VertexAttribute.MODEL_VIEW_MATRIX_2;
import static piengine.io.interpreter.vertexarray.VertexAttribute.MODEL_VIEW_MATRIX_3;
import static piengine.io.interpreter.vertexarray.VertexAttribute.MODEL_VIEW_MATRIX_4;
import static piengine.io.interpreter.vertexarray.VertexAttribute.TEXTURE_OFFSET;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class ParticleSystemService extends SupplierService<ParticleSystemKey, ParticleSystem> implements Initializable, Updatable {

    static final int MAX_INSTANCES = 10_000;
    static final int INSTANCE_DATA_LENGTH = 21;
    private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};

    private final List<ParticleSystem> particleSystems;
    private final SpriteService spriteService;
    private VertexArray particleSystemVertexArray;

    @Wire
    public ParticleSystemService(final SpriteService spriteService) {
        this.spriteService = spriteService;
        this.particleSystems = new ArrayList<>();
    }

    @Override
    public void initialize() {
        this.particleSystemVertexArray = createVertexArray();
    }

    @Override
    public ParticleSystem supply(final ParticleSystemKey key) {
        Sprite sprite = spriteService.supply(new SpriteKey(key.sprite, key.spriteSize));
        ParticleSystem particleSystem = new ParticleSystem(key.parent, particleSystemVertexArray, sprite, key.camera, key.pps, key.speed, key.gravityComplient, key.lifeLength);
        particleSystems.add(particleSystem);

        return particleSystem;
    }

    @Override
    public void terminate() {
        particleSystemVertexArray.clear();
    }

    @Override
    public void update(final float delta) {
        particleSystems.forEach(particleSystem -> particleSystem.update(delta));
    }

    private VertexArray createVertexArray() {
        VertexArray vertexArray = new VertexArray(VERTICES.length / 2);

        return vertexArray
                .bind()
                .attachVertexBuffer(VERTEX, VERTICES, 2)
                .attachVertexBuffer(EMPTY, vertexArray.createVertexBuffer()
                        .bind()
                        .attachFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH)
                        .attachAttribute(MODEL_VIEW_MATRIX_1, 4, INSTANCE_DATA_LENGTH, 0)
                        .attachAttribute(MODEL_VIEW_MATRIX_2, 4, INSTANCE_DATA_LENGTH, 4)
                        .attachAttribute(MODEL_VIEW_MATRIX_3, 4, INSTANCE_DATA_LENGTH, 8)
                        .attachAttribute(MODEL_VIEW_MATRIX_4, 4, INSTANCE_DATA_LENGTH, 12)
                        .attachAttribute(TEXTURE_OFFSET, 4, INSTANCE_DATA_LENGTH, 16)
                        .attachAttribute(BLEND, 1, INSTANCE_DATA_LENGTH, 20)
                        .unbind()
                )
                .unbind();
    }
}
