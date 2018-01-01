package piengine.visual.render.domain.context;

import org.joml.Vector2i;
import piengine.object.model.domain.Model;

public class GuiRenderContext implements RenderContext {

    public final Vector2i viewport;
    public final Model[] models;

    public GuiRenderContext(final Vector2i viewport, final Model[] models) {
        this.viewport = viewport;
        this.models = models;
    }
}
