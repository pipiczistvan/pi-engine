package piengine.visual.writing.text.domain;

import piengine.core.base.domain.Domain;
import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.visual.writing.font.domain.Font;

public class Text extends Entity implements Domain {

    public VertexArray vao;
    public Font font;
    public Color color;

    public Text(final Entity parent, final VertexArray vao, final Font font, final Color color) {
        super(parent);
        this.vao = vao;
        this.font = font;
        this.color = color;
    }
}
