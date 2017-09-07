package piengine.visual.writing.text.domain;

import java.util.ArrayList;
import java.util.List;

public class Word {

    public final double fontSize;
    public final List<Character> characters;
    public double width;

    public Word(double fontSize) {
        this.fontSize = fontSize;
        this.characters = new ArrayList<>();
        this.width = 0;
    }

    public void addCharacter(final Character character) {
        characters.add(character);
        width += character.xAdvance * fontSize;
    }

}
