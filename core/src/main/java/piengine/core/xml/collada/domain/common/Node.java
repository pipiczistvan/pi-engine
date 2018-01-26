package piengine.core.xml.collada.domain.common;

import piengine.core.base.exception.PIEngineException;
import piengine.core.xml.adapter.FloatArrayAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Node {

    @XmlAttribute(name = "id")
    public String id;

    @XmlElement(name = "node")
    public Node[] node = new Node[0];

    @XmlElement(name = "matrix")
    @XmlJavaTypeAdapter(FloatArrayAdapter.class)
    public float[] matrix = new float[0];

    public static Node findNodeById(final Node[] nodes, final String id) {
        for (Node node : nodes) {
            if (node.id.equals(id)) {
                return node;
            }
        }

        throw new PIEngineException("Could not find node (%s) in collada file!", id);
    }
}
