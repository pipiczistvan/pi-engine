package piengine.visual.shader.domain;

import piengine.core.base.domain.Dao;

public class ShaderDao implements Dao {

    public final Integer programId;
    public final Integer vertexShaderId;
    public final Integer tessControlShaderId;
    public final Integer tessEvalShaderId;
    public final Integer geometryShaderId;
    public final Integer fragmentShaderId;

    public ShaderDao(final Integer programId, final Integer vertexShaderId,
                     final Integer tessControlShaderId, final Integer tessEvalShaderId,
                     final Integer geometryShaderId, final Integer fragmentShaderId) {
        this.programId = programId;
        this.vertexShaderId = vertexShaderId;
        this.tessControlShaderId = tessControlShaderId;
        this.tessEvalShaderId = tessEvalShaderId;
        this.geometryShaderId = geometryShaderId;
        this.fragmentShaderId = fragmentShaderId;
    }

}
