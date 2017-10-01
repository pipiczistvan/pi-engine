package piengine.visual.writing.text.domain;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.base.domain.Domain;
import piengine.object.mesh.domain.MeshDao;
import piengine.visual.writing.font.domain.Font;

public class Text extends Domain<MeshDao> {

    public final Font font;
    public final Vector2f translation;
    public final Vector3f color;

    public Text(final MeshDao dao, final Font font, final Vector2f translation, final Vector3f color) {
        super(dao);

        this.font = font;
        this.translation = translation;
        this.color = color;
    }

}
