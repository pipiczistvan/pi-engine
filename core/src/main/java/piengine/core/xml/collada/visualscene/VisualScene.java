package piengine.core.xml.collada.visualscene;

import piengine.core.xml.collada.common.Node;

import javax.xml.bind.annotation.XmlElement;

public class VisualScene {

    @XmlElement(name = "node")
    public Node[] node = new Node[0];
}
