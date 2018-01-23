package piengine.core.xml.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlNode {

    private String name;
    private Map<String, String> attributes;
    private String data;
    private Map<String, List<XmlNode>> childNodes;

    public XmlNode(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getAttribute(final String attr) {
        if (attributes != null) {
            return attributes.get(attr);
        } else {
            return null;
        }
    }

    public XmlNode getChild(final String childName) {
        if (childNodes != null) {
            List<XmlNode> nodes = childNodes.get(childName);
            if (nodes != null && !nodes.isEmpty()) {
                return nodes.get(0);
            }
        }
        return null;

    }

    public XmlNode getChildWithAttribute(final String childName, final String attr, final String value) {
        List<XmlNode> children = getChildren(childName);
        if (children == null || children.isEmpty()) {
            return null;
        }
        for (XmlNode child : children) {
            String val = child.getAttribute(attr);
            if (value.equals(val)) {
                return child;
            }
        }
        return null;
    }

    public List<XmlNode> getChildren(final String name) {
        if (childNodes != null) {
            List<XmlNode> children = childNodes.get(name);
            if (children != null) {
                return children;
            }
        }
        return new ArrayList<>();
    }

    public void addAttribute(final String attr, final String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(attr, value);
    }

    public void addChild(final XmlNode child) {
        if (childNodes == null) {
            childNodes = new HashMap<>();
        }
        childNodes.computeIfAbsent(child.name, k -> new ArrayList<>()).add(child);
    }

    public void setData(final String data) {
        this.data = data;
    }
}
