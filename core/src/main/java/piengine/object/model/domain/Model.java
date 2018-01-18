package piengine.object.model.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.visual.texture.domain.Texture;

public class Model extends Entity {

    public final Mesh mesh;
    public Texture texture;
    public Color color;
    public final boolean lightEmitter;

    public Model(final Entity parent, final Mesh mesh, final Texture texture, final Color color, final boolean lightEmitter) {
        super(parent);
        this.mesh = mesh;
        this.texture = texture;
        this.color = color;
        this.lightEmitter = lightEmitter;
    }
}
