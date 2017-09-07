package piengine.core.property.domain;

import piengine.core.base.exception.PIEngineException;

import java.util.Properties;

abstract class Property<T> {

    final String key;

    Property(final String key) {
        this.key = key;
        PropertyKeys.KEYS.add(key);
    }

    T getValue(final Properties properties) {
        String value = properties.getProperty(key);
        try {
            return parseValue(value);
        } catch (Exception e) {
            throw new PIEngineException("Could not parse value %s of property %s!", value, key);
        }
    }

    abstract T parseValue(final String property);

}
