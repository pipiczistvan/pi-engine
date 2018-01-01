package piengine.visual.writing.text.domain;

import org.joml.Vector2i;
import piengine.visual.render.domain.context.RenderContext;

public class TextRenderContext implements RenderContext {

    public final Vector2i viewport;
    public final Text[] texts;

    public TextRenderContext(final Vector2i viewport, final Text[] texts) {
        this.viewport = viewport;
        this.texts = texts;
    }
}
