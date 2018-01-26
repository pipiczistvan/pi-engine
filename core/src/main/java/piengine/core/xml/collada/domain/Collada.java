package piengine.core.xml.collada.domain;

import piengine.core.xml.collada.domain.animation.Animation;
import piengine.core.xml.collada.domain.controller.Controller;
import piengine.core.xml.collada.domain.geometry.Geometry;
import piengine.core.xml.collada.domain.visualscene.VisualScene;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "COLLADA")
public class Collada {

    @XmlElementWrapper(name = "library_geometries")
    @XmlElement(name = "geometry")
    public Geometry[] library_geometries = new Geometry[0];

    @XmlElementWrapper(name = "library_controllers")
    @XmlElement(name = "controller")
    public Controller[] library_controllers = new Controller[0];

    @XmlElementWrapper(name = "library_visual_scenes")
    @XmlElement(name = "visual_scene")
    public VisualScene[] library_visual_scenes = new VisualScene[0];

    @XmlElementWrapper(name = "library_animations")
    @XmlElement(name = "animation")
    public Animation[] library_animations = new Animation[0];
}
