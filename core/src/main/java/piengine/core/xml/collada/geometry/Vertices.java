package piengine.core.xml.collada.geometry;

import piengine.core.xml.collada.common.Input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Vertices {

    @XmlAttribute(name = "id")
    public String id;

    @XmlElement(name = "input")
    public Input input;
}
