package piengine.visual.shader.domain;

import piengine.core.base.domain.ResourceData;

public class ShaderData implements ResourceData {

    public final String vertexSource;
    public final String geometrySource;
    public final String fragmentSource;

    public ShaderData(final String vertexSource, final String geometrySource, final String fragmentSource) {
        this.vertexSource = vertexSource;
        this.geometrySource = geometrySource;
        this.fragmentSource = fragmentSource;
    }

}
