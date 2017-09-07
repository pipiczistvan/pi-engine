package piengine.core.property.domain;

class BooleanProperty extends Property<Boolean> {

    BooleanProperty(final String key) {
        super(key);
    }

    @Override
    Boolean parseValue(final String property) {
        return Boolean.parseBoolean(property);
    }

}
