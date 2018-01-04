package piengine.core.base.type.property;

class IntegerProperty extends Property<Integer> {

    IntegerProperty(final String key) {
        super(key);
    }

    @Override
    Integer parseValue(final String property) {
        return Integer.parseInt(property);
    }

}