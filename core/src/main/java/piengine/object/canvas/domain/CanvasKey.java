package piengine.object.canvas.domain;

import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.postprocessing.domain.EffectType;

public class CanvasKey {

    public final Entity parent;
    public final Framebuffer framebuffer;
    public final Color color;
    public final EffectType[] effects;

    public CanvasKey(final Entity parent, final Framebuffer framebuffer, final Color color, final EffectType[] effects) {
        this.parent = parent;
        this.framebuffer = framebuffer;
        this.color = color;
        this.effects = effects;
    }
}
