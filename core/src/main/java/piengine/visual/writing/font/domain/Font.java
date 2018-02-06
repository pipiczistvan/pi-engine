package piengine.visual.writing.font.domain;

import piengine.core.base.domain.Domain;
import piengine.io.interpreter.texture.Texture;
import piengine.io.loader.fnt.FontDto;

public class Font extends Texture implements Domain {

    public final FontDto fontDto;

    public Font(final int width, final int height, final FontDto fontDto) {
        super(width, height);
        this.fontDto = fontDto;
    }
}
