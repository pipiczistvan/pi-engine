package piengine.core.base.type.property;

public class EnumProperty<E extends Enum<E>> extends Property<E> {

    private final Class<E> enumType;

    public EnumProperty(final String key, final Class<E> enumType) {
        super(key);
        this.enumType = enumType;
    }

    @Override
    E parseValue(final String property) {
        return E.valueOf(enumType, property);
    }
}
