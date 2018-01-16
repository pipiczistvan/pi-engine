package piengine.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {
    }

    public static List<String> findAllOccurrences(final Pattern pattern, final String text) {
        Matcher matcher = pattern.matcher(text);
        List<String> occurrences = new ArrayList<>();

        while (matcher.find()) {
            occurrences.add(matcher.group());
        }

        return occurrences;
    }
}
