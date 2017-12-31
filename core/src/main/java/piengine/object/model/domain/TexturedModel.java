package piengine.object.model.domain;

import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.visual.texture.domain.Texture;

public class TexturedModel extends Model {

    public Texture texture;

    public TexturedModel(final Mesh mesh, final Entity parent, final Texture texture) {
        super(mesh, parent);
        this.texture = texture;
    }
}
