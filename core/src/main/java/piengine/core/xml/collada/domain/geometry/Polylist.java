package piengine.core.xml.collada.domain.geometry;

import piengine.core.xml.adapter.IntegerArrayAdapter;
import piengine.core.xml.collada.domain.common.Input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Polylist {

    @XmlAttribute(name = "count")
    public int count;

    @XmlElement(name = "input")
    public Input[] input;

    @XmlElement(name = "p")
    @XmlJavaTypeAdapter(IntegerArrayAdapter.class)
    public int[] p;
}
