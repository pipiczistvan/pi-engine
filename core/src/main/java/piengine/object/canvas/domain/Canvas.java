package piengine.object.canvas.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.visual.postprocessing.domain.PostProcessingEffect;
import piengine.visual.texture.domain.Texture;

import java.util.List;

public class Canvas extends Entity {

    public final Mesh mesh;
    public Texture texture;
    public Color color;
    public final List<PostProcessingEffect> effects;

    public Canvas(final Entity parent, final Mesh mesh, final Texture texture, final Color color, final List<PostProcessingEffect> effects) {
        super(parent);
        this.mesh = mesh;
        this.texture = texture;
        this.color = color;
        this.effects = effects;
    }
}
