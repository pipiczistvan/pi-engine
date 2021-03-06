package piengine.visual.writing.font.accessor;

import org.joml.Vector2i;
import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.base.type.property.ApplicationProperties;
import piengine.core.base.type.property.PropertyKeys;
import piengine.visual.writing.font.domain.FontData;
import piengine.visual.writing.font.domain.FontKey;
import piengine.visual.writing.text.accessor.TextAccessor;
import piengine.visual.writing.text.domain.Character;
import puppeteer.annotation.premade.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FontAccessor extends Accessor<FontKey, FontData> {

    private static final String ROOT = ApplicationProperties.get(PropertyKeys.FONTS_LOCATION);
    private static final String FONT_EXT = "fnt";

    private static final int PAD_TOP = 0;
    private static final int PAD_LEFT = 1;
    private static final int PAD_BOTTOM = 2;
    private static final int PAD_RIGHT = 3;

    private static final int DESIRED_PADDING = 8;

    private static final String SPLITTER = " ";
    private static final String NUMBER_SEPARATOR = ",";

    private final ResourceLoader loader;

    public FontAccessor() {
        this.loader = new ResourceLoader(ROOT, FONT_EXT);
    }

    @Override
    protected FontData accessResource(final FontKey key) {
        final String fontSource = loader.load(key.imageKey.file);
        final FontContext context = new FontContext(fontSource.split("\n"), key.resolution);

        return new FontData(key, key.imageKey, parseSource(context), context.spaceWidth);
    }

    @Override
    protected String getAccessInfo(final FontKey key, final FontData resource) {
        return String.format("File: %s", key.imageKey.file);
    }

    private Map<Integer, Character> parseSource(final FontContext context) {
        loadPaddingData(context);
        loadLineSizes(context);

        return loadCharacterData(context);
    }

    private void loadPaddingData(final FontContext context) {
        context.processNextLine();
        context.padding = context.getValuesOfVariable("padding");
        context.paddingWidth = context.padding[PAD_LEFT] + context.padding[PAD_RIGHT];
        context.paddingHeight = context.padding[PAD_TOP] + context.padding[PAD_BOTTOM];
    }

    private void loadLineSizes(final FontContext context) {
        context.processNextLine();
        int lineHeightPixels = context.getValueOfVariable("lineHeight") - context.paddingHeight;
        context.verticalPerPixelSize = TextAccessor.LINE_HEIGHT / (double) lineHeightPixels;
        context.horizontalPerPixelSize = context.verticalPerPixelSize / context.aspectRatio;
    }

    private Map<Integer, Character> loadCharacterData(final FontContext context) {
        final Map<Integer, Character> characterMap = new HashMap<>();
        context.imageWidth = context.getValueOfVariable("scaleW");

        context.processNextLine();
        context.processNextLine();
        while (context.processNextLine()) {
            Character c = loadCharacter(context);
            if (c != null) {
                characterMap.put(c.id, c);
            }
        }

        return characterMap;
    }

    private Character loadCharacter(final FontContext context) {
        int id = context.getValueOfVariable("texture");
        if (id == TextAccessor.SPACE_ASCII) {
            context.spaceWidth = (context.getValueOfVariable("xadvance") - context.paddingWidth) * context.horizontalPerPixelSize;
            return null;
        }
        double xTex = ((double) context.getValueOfVariable("x") + (context.padding[PAD_LEFT] - DESIRED_PADDING)) / context.imageWidth;
        double yTex = ((double) context.getValueOfVariable("y") + (context.padding[PAD_TOP] - DESIRED_PADDING)) / context.imageWidth;
        int width = context.getValueOfVariable("width") - (context.paddingWidth - (2 * DESIRED_PADDING));
        int height = context.getValueOfVariable("height") - ((context.paddingHeight) - (2 * DESIRED_PADDING));
        double quadWidth = width * context.horizontalPerPixelSize;
        double quadHeight = height * context.verticalPerPixelSize;
        double xTexSize = (double) width / context.imageWidth;
        double yTexSize = (double) height / context.imageWidth;
        double xOff = (context.getValueOfVariable("xoffset") + context.padding[PAD_LEFT] - DESIRED_PADDING) * context.horizontalPerPixelSize;
        double yOff = (context.getValueOfVariable("yoffset") + (context.padding[PAD_TOP] - DESIRED_PADDING)) * context.verticalPerPixelSize;
        double xAdvance = (context.getValueOfVariable("xadvance") - context.paddingWidth) * context.horizontalPerPixelSize;
        return new Character(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff, quadWidth, quadHeight, xAdvance);
    }

    private class FontContext {

        private final String[] source;
        private int lineIndex;

        private double aspectRatio;
        private double verticalPerPixelSize;
        private double horizontalPerPixelSize;
        private double spaceWidth;
        private int[] padding;
        private int paddingWidth;
        private int paddingHeight;
        private int imageWidth;
        private Map<String, String> values;

        private FontContext(final String[] source, final Vector2i resolution) {
            this.source = source;
            this.aspectRatio = (double) resolution.x / (double) resolution.y;
            if (resolution.x < resolution.y) {
                this.aspectRatio = 1d / this.aspectRatio;
            }
            this.values = new HashMap<>();
        }

        private boolean processNextLine() {
            values.clear();

            if (lineIndex < source.length) {
                for (String part : source[lineIndex].split(SPLITTER)) {
                    String[] valuePairs = part.split("=");
                    if (valuePairs.length == 2) {
                        values.put(valuePairs[0], valuePairs[1]);
                    }
                }

                lineIndex++;
                return true;
            } else {
                return false;
            }
        }

        private int getValueOfVariable(final String variable) {
            return Integer.parseInt(values.get(variable));
        }

        private int[] getValuesOfVariable(final String variable) {
            String[] numbers = values.get(variable).split(NUMBER_SEPARATOR);
            int[] actualValues = new int[numbers.length];
            for (int i = 0; i < actualValues.length; i++) {
                actualValues[i] = Integer.parseInt(numbers[i]);
            }
            return actualValues;
        }

    }

}
