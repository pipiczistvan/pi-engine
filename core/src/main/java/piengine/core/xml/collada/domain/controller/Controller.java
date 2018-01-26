package piengine.core.xml.collada.domain.controller;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Controller {

    @XmlAttribute(name = "id")
    public String id;

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "skin")
    public Skin skin;
}
