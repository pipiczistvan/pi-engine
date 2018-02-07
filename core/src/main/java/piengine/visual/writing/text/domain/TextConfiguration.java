package piengine.visual.writing.text.domain;

import org.joml.Vector2f;
import piengine.core.base.type.color.Color;
import piengine.visual.writing.font.domain.Font;

public class TextConfiguration {

    private String text;
    private Font font;
    private float fontSize;
    private float maxLineLength;
    private boolean centered;
    private Color color;
    private float width;
    private float edge;
    private float borderWidth;
    private float borderEdge;
    private Color outlineColor;
    private Vector2f offset;

    private TextConfiguration() {
        this.text = "";
        this.fontSize = 1;
        this.maxLineLength = 1;
        this.centered = true;
        this.color = new Color(1);
        this.width = 0.5f;
        this.edge = 0.1f;
        this.borderWidth = 0.5f;
        this.borderEdge = 0.4f;
        this.outlineColor = new Color(0.1f, 0.6f, 0.7f, 1.0f);
        this.offset = new Vector2f(0);
    }

    public static TextConfiguration textConfig() {
        return new TextConfiguration();
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

    public TextConfiguration withWidth(final float width) {
        this.width = width;
        return this;
    }

    public TextConfiguration withEdge(final float edge) {
        this.edge = edge;
        return this;
    }

    public TextConfiguration withBorderWidth(final float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public TextConfiguration withBorderEdge(final float borderEdge) {
        this.borderEdge = borderEdge;
        return this;
    }

    public TextConfiguration withOutlineColor(final Color outlineColor) {
        this.outlineColor = outlineColor;
        return this;
    }

    public TextConfiguration withOffset(final Vector2f offset) {
        this.offset = offset;
        return this;
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

    public float getWidth() {
        return width;
    }

    public float getEdge() {
        return edge;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public float getBorderEdge() {
        return borderEdge;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public Vector2f getOffset() {
        return offset;
    }
}
