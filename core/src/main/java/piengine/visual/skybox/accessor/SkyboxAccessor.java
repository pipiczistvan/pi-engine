package piengine.visual.skybox.accessor;

import piengine.core.base.api.Accessor;
import piengine.visual.skybox.domain.SkyboxData;
import piengine.visual.skybox.domain.SkyboxKey;
import puppeteer.annotation.premade.Component;

@Component
public class SkyboxAccessor implements Accessor<SkyboxKey, SkyboxData> {

    private static final float[] VERTICES = {
            -1f, 1f, -1f,
            -1f, -1f, -1f,
            1f, -1f, -1f,
            1f, -1f, -1f,
            1f, 1f, -1f,
            -1f, 1f, -1f,

            -1f, -1f, 1f,
            -1f, -1f, -1f,
            -1f, 1f, -1f,
            -1f, 1f, -1f,
            -1f, 1f, 1f,
            -1f, -1f, 1f,

            1f, -1f, -1f,
            1f, -1f, 1f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            1f, 1f, -1f,
            1f, -1f, -1f,

            -1f, -1f, 1f,
            -1f, 1f, 1f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            1f, -1f, 1f,
            -1f, -1f, 1f,

            -1f, 1f, -1f,
            1f, 1f, -1f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            -1f, 1f, 1f,
            -1f, 1f, -1f,

            -1f, -1f, -1f,
            -1f, -1f, 1f,
            1f, -1f, -1f,
            1f, -1f, -1f,
            -1f, -1f, 1f,
            1f, -1f, 1f
    };

    @Override
    public SkyboxData access(final SkyboxKey key) {
        float[] vertices = new float[VERTICES.length];
        for (int i = 0; i < VERTICES.length; i++) {
            vertices[i] = VERTICES[i] * key.size;
        }

        return new SkyboxData(vertices, key.cubeMap);
    }
}
