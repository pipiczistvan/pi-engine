package piengine.core.base.type.property;

import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final ResourceLoader resourceLoader = new ResourceLoader("config", "properties");

    public static void load(final String engineFile, final String applicationFile) {
        loadProperties(engineFile);
        loadProperties(applicationFile);

        validateProperties();
    }

    public static <T> T get(final Property<T> property) {
        return property.getValue(PROPERTIES);
    }

    public static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void overwriteSystemProperties(final Properties properties) {
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            String parameterProperty = System.getProperty(propertyName);
            if (parameterProperty != null) {
                properties.put(propertyName, parameterProperty);
            }
        }
    }

    private static void validateProperties() {
        final List<String> validationErrorMessages = new ArrayList<>();

        for (Object property : PROPERTIES.keySet()) {
            if (!isIn(PropertyKeys.KEYS, property)) {
                validationErrorMessages.add(String.format("\t- Unnecessary property: %s", property));
            }
        }

        for (Object property : PropertyKeys.KEYS) {
            if (!isIn(PROPERTIES.keySet(), property)) {
                validationErrorMessages.add(String.format("\t- Missing property: %s", property));
            }
        }

        if (!validationErrorMessages.isEmpty()) {
            StringBuilder errorMessageBuilder = new StringBuilder("Invalid property files!");

            for (String errorMessage : validationErrorMessages) {
                errorMessageBuilder.append('\n').append(errorMessage);
            }
            throw new PIEngineException(errorMessageBuilder.toString());
        }
    }

    private static boolean isIn(final Set<?> properties, final Object property) {
        return properties.contains(property);
    }

    private static void loadProperties(final String file) {
        Properties properties = new Properties();
        try (InputStream inputStream = resourceLoader.getUrl(file).openStream()) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PIEngineException("Could not find property file %s!", file);
        }
        overwriteSystemProperties(properties);

        PROPERTIES.putAll(properties);
    }
}
