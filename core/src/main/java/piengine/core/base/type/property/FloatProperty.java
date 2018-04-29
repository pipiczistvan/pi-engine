package piengine.core.base.type.property;

public class FloatProperty extends Property<Float> {

    public FloatProperty(final String key) {
        super(key);
    }

    @Override
    Float parseValue(final String property) {
        return Float.parseFloat(property);
    }
}
