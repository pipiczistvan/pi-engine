package piengine.core.property.domain;

class FloatProperty extends Property<Float> {

    FloatProperty(final String key) {
        super(key);
    }

    @Override
    Float parseValue(final String property) {
        return Float.parseFloat(property);
    }

}
