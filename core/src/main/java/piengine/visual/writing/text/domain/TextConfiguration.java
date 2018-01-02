package piengine.visual.writing.text.domain;

import org.joml.Vector2f;
import org.joml.Vector4f;
import piengine.visual.writing.font.domain.Font;

public class TextConfiguration {

    private String text;
    private Font font;
    private float fontSize;
    private float maxLineLength;
    private boolean centered;
    private Vector4f color;
    private Vector2f translation;

    private TextConfiguration() {
        this.text = "";
        this.fontSize = 1;
        this.maxLineLength = 1;
        this.centered = true;
        this.color = new Vector4f(1);
        this.translation = new Vector2f();
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

    public TextConfiguration withColor(final Vector4f color) {
        this.color = color;
        return this;
    }

    public TextConfiguration withTranslation(final Vector2f translation) {
        this.translation = translation;
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

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

    public Vector4f getColor() {
        return color;
    }

    public Vector2f getTranslation() {
        return translation;
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
