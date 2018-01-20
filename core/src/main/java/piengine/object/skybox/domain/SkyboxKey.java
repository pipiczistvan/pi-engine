package piengine.object.skybox.domain;

import piengine.core.base.domain.Key;
import piengine.visual.cubemap.domain.CubeMap;

import java.util.Objects;

public class SkyboxKey implements Key {

    public final float size;
    public final CubeMap cubeMap;

    public SkyboxKey(final float size, final CubeMap cubeMap) {
        this.size = size;
        this.cubeMap = cubeMap;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyboxKey skyboxKey = (SkyboxKey) o;
        return Float.compare(skyboxKey.size, size) == 0 &&
                Objects.equals(cubeMap, skyboxKey.cubeMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, cubeMap);
    }
}
