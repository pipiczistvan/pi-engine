package piengine.core.property.domain;

class StringProperty extends Property<String> {

    StringProperty(final String key) {
        super(key);
    }

    @Override
    String parseValue(final String property) {
        return property;
    }

}