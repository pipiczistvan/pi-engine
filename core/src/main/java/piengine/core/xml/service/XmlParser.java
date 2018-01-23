package piengine.core.xml.service;

import piengine.core.xml.domain.XmlNode;
import puppeteer.annotation.premade.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class XmlParser {

    private static final Pattern DATA = Pattern.compile(">(.+?)<");
    private static final Pattern START_TAG = Pattern.compile("<(.+?)>");
    private static final Pattern ATTR_NAME = Pattern.compile("(.+?)=");
    private static final Pattern ATTR_VAL = Pattern.compile("\"(.+?)\"");
    private static final Pattern CLOSED = Pattern.compile("(</|/>)");

    public XmlNode parseSource(final String[] source) {
        LineReader lineReader = new LineReader(source);
        lineReader.read();

        return loadNode(lineReader);
    }

    private XmlNode loadNode(final LineReader lineReader) {
        String line = lineReader.read();
        if (line.startsWith("</")) {
            return null;
        }
        String[] startTagParts = getStartTag(line).split(" ");
        XmlNode node = new XmlNode(startTagParts[0].replace("/", ""));
        addAttributes(startTagParts, node);
        addData(line, node);
        if (CLOSED.matcher(line).find()) {
            return node;
        }
        XmlNode child;
        while ((child = loadNode(lineReader)) != null) {
            node.addChild(child);
        }
        return node;
    }

    private void addData(final String line, final XmlNode node) {
        Matcher matcher = DATA.matcher(line);
        if (matcher.find()) {
            node.setData(matcher.group(1));
        }
    }

    private void addAttributes(final String[] titleParts, final XmlNode node) {
        for (int i = 1; i < titleParts.length; i++) {
            if (titleParts[i].contains("=")) {
                addAttribute(titleParts[i], node);
            }
        }
    }

    private void addAttribute(final String attributeLine, final XmlNode node) {
        Matcher nameMatch = ATTR_NAME.matcher(attributeLine);
        nameMatch.find();
        Matcher valMatch = ATTR_VAL.matcher(attributeLine);
        valMatch.find();
        node.addAttribute(nameMatch.group(1), valMatch.group(1));
    }

    private String getStartTag(final String line) {
        Matcher match = START_TAG.matcher(line);
        match.find();
        return match.group(1);
    }

    private class LineReader {

        private int index = 0;
        private final String[] lines;

        private LineReader(final String[] lines) {
            this.lines = lines;
        }

        private String read() {
            return lines[index++];
        }
    }
}
