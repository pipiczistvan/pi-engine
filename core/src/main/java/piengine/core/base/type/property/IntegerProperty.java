package piengine.core.base.type.property;

public class IntegerProperty extends Property<Integer> {

    public IntegerProperty(final String key) {
        super(key);
    }

    @Override
    Integer parseValue(final String property) {
        return Integer.parseInt(property);
    }
}