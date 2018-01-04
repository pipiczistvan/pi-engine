package piengine.core.base.type.property;

class StringProperty extends Property<String> {

    StringProperty(final String key) {
        super(key);
    }

    @Override
    String parseValue(final String property) {
        return property;
    }

}