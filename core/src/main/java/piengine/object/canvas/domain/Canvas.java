package piengine.object.canvas.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.object.mesh.domain.Mesh;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;
import piengine.visual.texture.domain.Texture;

import java.util.List;

public class Canvas extends Entity {

    public final CanvasKey key;
    public final Mesh mesh;
    public final List<PostProcessingEffectContext> effectContexts;
    public Texture texture;
    public Color color;

    public Canvas(final Entity parent, final CanvasKey key, final Mesh mesh, final List<PostProcessingEffectContext> effectContexts, final Texture texture, final Color color) {
        super(parent);
        this.key = key;
        this.mesh = mesh;
        this.effectContexts = effectContexts;
        this.texture = texture;
        this.color = color;
    }
}
