package piengine.core.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class FloatArrayAdapter extends XmlAdapter<String, float[]> {

    @Override
    public float[] unmarshal(final String value) {
        String[] fragments = value.split(" ");
        float[] values = new float[fragments.length];

        for (int i = 0; i < values.length; i++) {
            values[i] = Float.parseFloat(fragments[i]);
        }

        return values;
    }

    @Override
    public String marshal(float[] value) {
        return Arrays.toString(value);
    }
}
