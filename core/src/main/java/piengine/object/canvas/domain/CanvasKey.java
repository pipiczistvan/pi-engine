package piengine.object.canvas.domain;

import piengine.core.base.type.color.Color;
import piengine.object.entity.domain.Entity;
import piengine.visual.postprocessing.domain.EffectType;
import piengine.visual.texture.domain.Texture;

public class CanvasKey {

    public final Entity parent;
    public final Texture texture;
    public final Color color;
    public final EffectType[] effects;

    public CanvasKey(final Entity parent, final Texture texture, final Color color, final EffectType[] effects) {
        this.parent = parent;
        this.texture = texture;
        this.color = color;
        this.effects = effects;
    }
}
