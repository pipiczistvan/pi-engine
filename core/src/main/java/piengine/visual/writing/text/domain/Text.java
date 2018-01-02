package piengine.visual.writing.text.domain;

import org.joml.Vector4f;
import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.MeshDao;
import piengine.visual.writing.font.domain.Font;

public class Text extends Entity {

    public final MeshDao dao;
    public final Font font;
    public final Vector4f color;

    public Text(final MeshDao dao, final Font font, final Vector4f color, final Entity parent) {
        super(parent);

        this.dao = dao;
        this.font = font;
        this.color = color;
    }
}
