package piengine.visual.writing.font.domain;

import piengine.core.base.domain.ResourceData;
import piengine.visual.image.domain.ImageKey;
import piengine.visual.writing.text.domain.Character;

import java.util.Map;

public class FontData implements ResourceData {

    public final FontKey key;
    public final ImageKey imageKey;
    public final Map<Integer, Character> characterMap;
    public final double spaceWidth;

    public FontData(final FontKey key, final ImageKey imageKey, final Map<Integer, Character> characterMap, final double spaceWidth) {
        this.key = key;
        this.imageKey = imageKey;
        this.characterMap = characterMap;
        this.spaceWidth = spaceWidth;
    }
}
