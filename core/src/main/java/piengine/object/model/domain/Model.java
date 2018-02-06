package piengine.object.model.domain;

import piengine.core.base.domain.Domain;
import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.texture.Texture;
import piengine.io.interpreter.vertexarray.VertexArray;

public class Model extends Entity implements Domain {

    public final VertexArray vao;
    public Texture texture;
    public final Color color;
    public final boolean lightEmitter;

    public Model(final Entity parent, final VertexArray vao, final Texture texture, final Color color, final boolean lightEmitter) {
        super(parent);
        this.vao = vao;
        this.texture = texture;
        this.color = color;
        this.lightEmitter = lightEmitter;
    }
}
