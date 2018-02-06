package piengine.core.xml.collada.animation;

import piengine.core.xml.collada.common.Input;

import javax.xml.bind.annotation.XmlElement;

public class Sampler {

    @XmlElement(name = "input")
    public Input[] input = new Input[0];
}
