package piengine.core.xml.collada.common;

import piengine.core.base.exception.PIEngineException;
import piengine.core.xml.adapter.FloatArrayAdapter;
import piengine.core.xml.adapter.StringArrayAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Source {

    @XmlAttribute(name = "id")
    public String id;

    @XmlElement(name = "float_array")
    @XmlJavaTypeAdapter(FloatArrayAdapter.class)
    public float[] float_array = new float[0];

    @XmlElement(name = "Name_array")
    @XmlJavaTypeAdapter(StringArrayAdapter.class)
    public String[] Name_array = new String[0];

    public static Source findSourceById(final Source[] sources, final String id) {
        for (Source source : sources) {
            if (source.id.equals(id)) {
                return source;
            }
        }

        throw new PIEngineException("Could not find source (%s) in collada file!", id);
    }
}
