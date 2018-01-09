package piengine.visual.skybox.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.cubemap.domain.CubeMap;

public class SkyboxData implements ResourceData {

    public final float[] vertices;
    public final CubeMap cubeMap;

    public SkyboxData(final float[] vertices, final CubeMap cubeMap) {
        this.vertices = vertices;
        this.cubeMap = cubeMap;
    }
}
