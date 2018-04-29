package piengine.core.base.type.property;

public class StringProperty extends Property<String> {

    public StringProperty(final String key) {
        super(key);
    }

    @Override
    String parseValue(final String property) {
        return property;
    }
}