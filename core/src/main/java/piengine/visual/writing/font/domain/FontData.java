package piengine.visual.writing.font.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.writing.text.domain.Character;

import java.util.Map;

public class FontData implements ResourceData {

    public final String file;
    public final Map<Integer, Character> characterMap;
    public final double spaceWidth;

    public FontData(final String file, final Map<Integer, Character> characterMap, final double spaceWidth) {
        this.file = file;
        this.characterMap = characterMap;
        this.spaceWidth = spaceWidth;
    }

}
