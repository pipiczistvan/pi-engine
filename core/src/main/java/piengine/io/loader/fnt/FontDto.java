package piengine.io.loader.fnt;

import piengine.io.loader.Dto;
import piengine.io.loader.text.domain.Character;

import java.util.Map;

public class FontDto implements Dto {

    public final Map<Integer, Character> characterMap;
    public final double spaceWidth;

    public FontDto(final Map<Integer, Character> characterMap, final double spaceWidth) {
        this.characterMap = characterMap;
        this.spaceWidth = spaceWidth;
    }
}
