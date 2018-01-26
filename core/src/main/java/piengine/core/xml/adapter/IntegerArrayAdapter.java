package piengine.core.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class IntegerArrayAdapter extends XmlAdapter<String, int[]> {

    @Override
    public int[] unmarshal(final String value) {
        String[] fragments = value.split(" ");
        int[] values = new int[fragments.length];

        for (int i = 0; i < values.length; i++) {
            values[i] = Integer.parseInt(fragments[i]);
        }

        return values;
    }

    @Override
    public String marshal(int[] value) {
        return Arrays.toString(value);
    }
}
