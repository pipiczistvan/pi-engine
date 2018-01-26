package piengine.core.xml.collada.domain.geometry;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Geometry {

    @XmlAttribute(name = "id")
    public String id;

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "mesh")
    public Mesh mesh;
}
