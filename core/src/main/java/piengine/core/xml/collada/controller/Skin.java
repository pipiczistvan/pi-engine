package piengine.core.xml.collada.controller;

import piengine.core.xml.collada.common.Source;

import javax.xml.bind.annotation.XmlElement;

public class Skin {

    @XmlElement(name = "source")
    public Source[] source;

    @XmlElement(name = "vertex_weights")
    public VertexWeights vertex_weights;
}
