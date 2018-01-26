package piengine.core.xml.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class StringArrayAdapter extends XmlAdapter<String, String[]> {

    @Override
    public String[] unmarshal(final String value) {
        return value.split(" ");
    }

    @Override
    public String marshal(String[] value) {
        return Arrays.toString(value);
    }
}
