package piengine.core.xml.collada;

import piengine.core.xml.collada.animation.Animation;
import piengine.core.xml.collada.controller.Controller;
import piengine.core.xml.collada.geometry.Geometry;
import piengine.core.xml.collada.visualscene.VisualScene;

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
