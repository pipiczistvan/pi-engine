package piengine.object.model.domain;

import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;

public class Model extends Entity {

    public final Mesh mesh;
    public final ModelAttribute attribute;

    public Model(final Mesh mesh, final ModelAttribute attribute, final Entity parent) {
        super(parent);

        this.mesh = mesh;
        this.attribute = attribute;
    }

}
