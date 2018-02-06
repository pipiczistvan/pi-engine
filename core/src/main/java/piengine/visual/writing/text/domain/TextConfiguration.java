package piengine.visual.writing.text.domain;

import piengine.core.base.domain.Entity;
import piengine.core.base.type.color.Color;
import piengine.visual.writing.font.domain.Font;

public class TextConfiguration {

    private final Entity parent;
    private String text;
    private Font font;
    private float fontSize;
    private float maxLineLength;
    private boolean centered;
    private Color color;

    private TextConfiguration(final Entity parent) {
        this.parent = parent;
        this.text = "";
        this.fontSize = 1;
        this.maxLineLength = 1;
        this.centered = true;
        this.color = new Color(1);
    }

    public static TextConfiguration textConfig(final Entity parent) {
        return new TextConfiguration(parent);
    }

    public TextConfiguration withText(final String text) {
        this.text = text;
        return this;
    }

    public TextConfiguration withFont(final Font font) {
        this.font = font;
        return this;
    }

    public TextConfiguration withColor(final Color color) {
        this.color = color;
        return this;
    }

    public TextConfiguration withFontSize(final float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public TextConfiguration withMaxLineLength(final float maxLineLength) {
        this.maxLineLength = maxLineLength;
        return this;
    }

    public TextConfiguration withCentered(final boolean centered) {
        this.centered = centered;
        return this;
    }

    public Entity getParent() {
        return parent;
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public float getFontSize() {
        return fontSize;
    }

    public float getMaxLineLength() {
        return maxLineLength;
    }

    public boolean isCentered() {
        return centered;
    }

}
