package piengine.visual.writing.text.domain;

import java.util.ArrayList;
import java.util.List;

public class Line {

    public final double maxLength;
    public final double spaceSize;
    public final List<Word> words;

    private double currentLineLength;

    public Line(final double spaceWidth, final double fontSize, final double maxLength) {
        this.spaceSize = spaceWidth * fontSize;
        this.maxLength = maxLength;
        this.words = new ArrayList<>();
        this.currentLineLength = 0;
    }

    public boolean attemptToAddWord(Word word) {
        double additionalLength = word.width;
        additionalLength += !words.isEmpty() ? spaceSize : 0;
        if (currentLineLength + additionalLength <= maxLength) {
            words.add(word);
            currentLineLength += additionalLength;
            return true;
        } else {
            return false;
        }
    }

    public double getLineLength() {
        return currentLineLength;
    }

}
