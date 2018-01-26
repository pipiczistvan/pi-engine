package piengine.core.xml.collada.domain.controller;

import piengine.core.xml.adapter.IntegerArrayAdapter;
import piengine.core.xml.collada.domain.common.Input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class VertexWeights {

    @XmlAttribute(name = "count")
    public int count;

    @XmlElement(name = "input")
    public Input[] input = new Input[0];

    @XmlElement(name = "vcount")
    @XmlJavaTypeAdapter(IntegerArrayAdapter.class)
    public int[] vcount = new int[0];

    @XmlElement(name = "v")
    @XmlJavaTypeAdapter(IntegerArrayAdapter.class)
    public int[] v = new int[0];
}
