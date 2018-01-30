package piengine.object.particlesystem.domain;

import piengine.core.base.domain.ResourceData;

public class ParticleSystemData implements ResourceData {

    public final ParticleSystemKey key;
    public final float[] vertices;

    public ParticleSystemData(final ParticleSystemKey key, final float[] vertices) {
        this.key = key;
        this.vertices = vertices;
    }
}
