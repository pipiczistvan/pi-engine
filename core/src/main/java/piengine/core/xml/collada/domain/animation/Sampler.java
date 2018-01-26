package piengine.core.xml.collada.domain.animation;

import piengine.core.xml.collada.domain.common.Input;

import javax.xml.bind.annotation.XmlElement;

public class Sampler {

    @XmlElement(name = "input")
    public Input[] input = new Input[0];
}
