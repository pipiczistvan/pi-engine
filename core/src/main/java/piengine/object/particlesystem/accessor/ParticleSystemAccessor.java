package piengine.object.particlesystem.accessor;

import piengine.core.base.api.Accessor;
import piengine.object.particlesystem.domain.ParticleSystemData;
import piengine.object.particlesystem.domain.ParticleSystemKey;
import puppeteer.annotation.premade.Component;

@Component
public class ParticleSystemAccessor implements Accessor<ParticleSystemKey, ParticleSystemData> {

    private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};

    @Override
    public ParticleSystemData access(final ParticleSystemKey key) {
        return new ParticleSystemData(key, VERTICES);
    }
}
