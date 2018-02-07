package piengine.object.skybox.accessor;

import piengine.core.base.api.Accessor;
import piengine.object.skybox.domain.SkyboxData;
import piengine.object.skybox.domain.SkyboxKey;
import puppeteer.annotation.premade.Component;

@Component
public class SkyboxAccessor extends Accessor<SkyboxKey, SkyboxData> {

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
    protected SkyboxData accessResource(final SkyboxKey key) {
        float[] vertices = new float[VERTICES.length];
        for (int i = 0; i < VERTICES.length; i++) {
            vertices[i] = VERTICES[i] * key.size;
        }

        return new SkyboxData(vertices, key.cubeMap);
    }

    @Override
    protected String getAccessInfo(final SkyboxKey key, final SkyboxData resource) {
        return String.format("CubeMap texture id: %s", key.cubeMap.getDao().getTexture());
    }
}
