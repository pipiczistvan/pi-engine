package piengine.core.base.type.property;

public class BooleanProperty extends Property<Boolean> {

    public BooleanProperty(final String key) {
        super(key);
    }

    @Override
    Boolean parseValue(final String property) {
        return Boolean.parseBoolean(property);
    }
}
