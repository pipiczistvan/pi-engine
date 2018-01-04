package piengine.core.base.type.property;

class FloatProperty extends Property<Float> {

    FloatProperty(final String key) {
        super(key);
    }

    @Override
    Float parseValue(final String property) {
        return Float.parseFloat(property);
    }

}
