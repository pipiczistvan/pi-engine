package piengine.core.xml.collada.domain.controller;

import piengine.core.xml.collada.domain.common.Source;

import javax.xml.bind.annotation.XmlElement;

public class Skin {

    @XmlElement(name = "source")
    public Source[] source;

    @XmlElement(name = "vertex_weights")
    public VertexWeights vertex_weights;
}
