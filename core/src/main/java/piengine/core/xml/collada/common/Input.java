package piengine.core.xml.collada.common;

import piengine.core.base.exception.PIEngineException;

import javax.xml.bind.annotation.XmlAttribute;

public class Input {

    @XmlAttribute(name = "semantic")
    public String semantic;

    @XmlAttribute(name = "source")
    public String source;

    public static Input findInputBySemantic(final Input[] inputs, final String semantic) {
        for (Input input : inputs) {
            if (input.semantic.equals(semantic)) {
                return input;
            }
        }

        throw new PIEngineException("Could not find input (%s) in collada file!", semantic);
    }
}
