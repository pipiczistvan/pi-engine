package piengine.visual.writing.text.accessor;

import piengine.visual.writing.text.domain.Character;
import piengine.visual.writing.text.domain.Line;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.domain.TextData;
import piengine.visual.writing.text.domain.Word;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextAccessor {

    public static final double LINE_HEIGHT = 0.03f;
    public static final int SPACE_ASCII = 32;

    public TextData access(final TextConfiguration config) {
        List<Line> lines = createStructure(config);
        return createQuadVertices(config, lines);
    }

    private List<Line> createStructure(final TextConfiguration config) {
        char[] chars = config.getText().toCharArray();
        List<Line> lines = new ArrayList<>();
        Line currentLine = new Line(config.getFont().data.spaceWidth, config.getFontSize(), config.getMaxLineLength());
        Word currentWord = new Word(config.getFontSize());
        for (char c : chars) {
            int ascii = (int) c;
            if (ascii == SPACE_ASCII) {
                boolean added = currentLine.attemptToAddWord(currentWord);
                if (!added) {
                    lines.add(currentLine);
                    currentLine = new Line(config.getFont().data.spaceWidth, config.getFontSize(), config.getMaxLineLength());
                    currentLine.attemptToAddWord(currentWord);
                }
                currentWord = new Word(config.getFontSize());
                continue;
            }
            Character character = config.getFont().data.characterMap.get(ascii);
            currentWord.addCharacter(character);
        }
        completeStructure(lines, currentLine, currentWord, config);
        return lines;
    }

    private void completeStructure(final List<Line> lines, Line currentLine, final Word currentWord, final TextConfiguration config) {
        boolean added = currentLine.attemptToAddWord(currentWord);
        if (!added) {
            lines.add(currentLine);
            currentLine = new Line(config.getFont().data.spaceWidth, config.getFontSize(), config.getMaxLineLength());
            currentLine.attemptToAddWord(currentWord);
        }
        lines.add(currentLine);
    }

    private TextData createQuadVertices(final TextConfiguration config, final List<Line> lines) {
        double cursorX = 0f;
        double cursorY = 0f;
        List<Float> vertices = new ArrayList<>();
        List<Float> textureCoords = new ArrayList<>();

        for (Line line : lines) {
            if (config.isCentered()) {
                cursorX = (line.maxLength - line.getLineLength()) / 2;
            }
            for (Word word : line.words) {
                for (Character letter : word.characters) {
                    addVerticesForCharacter(vertices, cursorX, cursorY, letter, config.getFontSize());
                    addTexCoords(textureCoords, letter.xTextureCoord, letter.yTextureCoord, letter.xMaxTextureCoord, letter.yMaxTextureCoord);

                    cursorX += letter.xAdvance * config.getFontSize();
                }
                cursorX += config.getFont().data.spaceWidth * config.getFontSize();
            }
            cursorX = 0;
            cursorY += LINE_HEIGHT * config.getFontSize();
        }
        return new TextData(listToArray(vertices), listToArray(textureCoords));
    }

    private void addVerticesForCharacter(final List<Float> vertices, final double cursorX, final double cursorY, final Character character, final double fontSize) {
        double x = cursorX + (character.xOffset * fontSize);
        double y = cursorY + (character.yOffset * fontSize);
        double maxX = x + (character.sizeX * fontSize);
        double maxY = y + (character.sizeY * fontSize);
        double properX = (2 * x) - 1;
        double properY = (-2 * y) + 1;
        double properMaxX = (2 * maxX) - 1;
        double properMaxY = (-2 * maxY) + 1;
        addVertices(vertices, properX, properY, properMaxX, properMaxY);
    }

    private static void addVertices(final List<Float> vertices, final double x, final double y, final double maxX, final double maxY) {
        vertices.add((float) x);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) y);
    }

    private static void addTexCoords(final List<Float> texCoords, final double x, final double y, final double maxX, final double maxY) {
        texCoords.add((float) x);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) y);
    }

    private static float[] listToArray(final List<Float> listOfFloats) {
        float[] array = new float[listOfFloats.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = listOfFloats.get(i);
        }
        return array;
    }

}
