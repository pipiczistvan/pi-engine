package piengine.object.particlesystem.domain;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.base.api.Updatable;
import piengine.core.base.domain.Entity;
import piengine.object.camera.domain.Camera;

public class Particle extends Entity implements Updatable {

    private static final float GRAVITY = -100;

    private final Camera camera;
    private final Vector3f velocity;
    private final float gravityEffect;
    private final float lifeLength;

    private final Vector2f textureOffsetCurrent = new Vector2f();
    private final Vector2f textureOffsetNext = new Vector2f();
    private final Vector2f textureInfo = new Vector2f();

    private float elapsedTime = 0;
    private float distance = 0;

    public Particle(final Entity parent, final Camera camera, final Vector3f velocity,
                    final float gravityEffect, final float lifeLength, final int spriteSize) {
        super(parent);
        this.camera = camera;
        this.velocity = velocity;
        this.gravityEffect = gravityEffect;
        this.lifeLength = lifeLength;
        this.textureInfo.x = spriteSize;
    }

    @Override
    public void update(final float delta) {
        velocity.y += GRAVITY * gravityEffect * delta;
        Vector3f change = new Vector3f(velocity);
        change.mul(delta);
        translate(change);

        distance = new Vector3f(camera.getPosition()).sub(getPosition()).lengthSquared();

        updateTextureCoordInfo();
        elapsedTime += delta;
    }

    public boolean isAlive() {
        return elapsedTime < lifeLength;
    }

    public Vector2f getTextureOffsetCurrent() {
        return textureOffsetCurrent;
    }

    public Vector2f getTextureOffsetNext() {
        return textureOffsetNext;
    }

    public Vector2f getTextureInfo() {
        return textureInfo;
    }

    public float getDistance() {
        return distance;
    }

    private void updateTextureCoordInfo() {
        float lifeFactor = elapsedTime / lifeLength;
        int stageCount = (int) (textureInfo.x * textureInfo.x);
        float atlasProgression = lifeFactor * stageCount;
        int indexCurrent = (int) Math.floor(atlasProgression);
        int indexNext = indexCurrent < stageCount - 1 ? indexCurrent + 1 : indexCurrent;

        textureInfo.y = atlasProgression % 1;
        setTextureOffset(textureOffsetCurrent, indexCurrent);
        setTextureOffset(textureOffsetNext, indexNext);
    }

    private void setTextureOffset(final Vector2f offset, final int index) {
        int column = (int) (index % textureInfo.x);
        int row = (int) (index / textureInfo.x);
        offset.x = column / textureInfo.x;
        offset.y = row / textureInfo.x;
    }
}
