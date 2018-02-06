package piengine.object.canvas.domain;

import piengine.core.base.domain.Domain;
import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.visual.postprocessing.domain.context.PostProcessingEffectContext;

import java.util.List;

public class Canvas extends Entity implements Domain {

    public final VertexArray vao;
    public final List<PostProcessingEffectContext> effectContexts;
    public Framebuffer framebuffer;
    public Color color;

    public Canvas(final Entity parent, final VertexArray vao, final List<PostProcessingEffectContext> effectContexts, final Framebuffer framebuffer, final Color color) {
        super(parent);
        this.vao = vao;
        this.effectContexts = effectContexts;
        this.framebuffer = framebuffer;
        this.color = color;
    }
}
