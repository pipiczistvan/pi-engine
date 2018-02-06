package piengine.io.loader.glsl.domain;

import piengine.io.loader.Dto;

public class GlslDto implements Dto {

    public final String vertexSource;
    public final String tessControlSource;
    public final String tessEvalSource;
    public final String geometrySource;
    public final String fragmentSource;

    public GlslDto(final String vertexSource, final String tessControlSource,
                      final String tessEvalSource, final String geometrySource,
                      final String fragmentSource) {
        this.vertexSource = vertexSource;
        this.tessControlSource = tessControlSource;
        this.tessEvalSource = tessEvalSource;
        this.geometrySource = geometrySource;
        this.fragmentSource = fragmentSource;
    }
}
