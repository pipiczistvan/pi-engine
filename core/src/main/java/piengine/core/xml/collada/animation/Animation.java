package piengine.core.xml.collada.animation;

import piengine.core.xml.collada.common.Source;

import javax.xml.bind.annotation.XmlElement;

public class Animation {

    @XmlElement(name = "source")
    public Source[] source = new Source[0];

    @XmlElement(name = "sampler")
    public Sampler sampler;

    @XmlElement(name = "channel")
    public Channel channel;
}
