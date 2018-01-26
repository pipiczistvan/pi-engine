package piengine.core.xml.collada.domain.visualscene;

import piengine.core.xml.collada.domain.common.Node;

import javax.xml.bind.annotation.XmlElement;

public class VisualScene {

    @XmlElement(name = "node")
    public Node[] node = new Node[0];
}
