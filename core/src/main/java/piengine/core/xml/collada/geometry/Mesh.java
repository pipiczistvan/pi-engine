package piengine.core.xml.collada.geometry;

import piengine.core.xml.collada.common.Source;

import javax.xml.bind.annotation.XmlElement;

public class Mesh {

    @XmlElement(name = "source")
    public Source[] source;

    @XmlElement(name = "vertices")
    public Vertices vertices;

    @XmlElement(name = "polylist")
    public Polylist polylist;
}
