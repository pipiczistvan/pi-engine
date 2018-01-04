package piengine.core.base.type.property;

import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();
    private static final ResourceLoader resourceLoader = new ResourceLoader("config", "properties");

    public static void load(final String engineFile, final String applicationFile) {
        Properties engineProperties = loadProperties(engineFile);
        Properties applicationProperties = loadProperties(applicationFile);
        Properties properties = mergeProperties(engineProperties, applicationProperties);

        validateProperties(engineProperties, applicationProperties, properties);

        PROPERTIES.putAll(properties);
    }

    public static <T> T get(final Property<T> property) {
        return property.getValue(PROPERTIES);
    }

    private static Properties mergeProperties(final Properties engineProperties, final Properties applicationProperties) {
        final Properties properties = new Properties();
        properties.putAll(engineProperties);
        properties.putAll(applicationProperties);

        return properties;
    }

    private static void validateProperties(final Properties engineProperties, final Properties applicationProperties, final Properties properties) {
        final List<String> validationErrorMessages = new ArrayList<>();

        for (String property : getDuplicatedProperties(engineProperties.keySet(), applicationProperties.keySet())) {
            validationErrorMessages.add(String.format("\t- Duplicated property: %s", property));
        }

        for (Object property : properties.keySet()) {
            if (!isIn(PropertyKeys.KEYS, property)) {
                validationErrorMessages.add(String.format("\t- Unnecessary property: %s", property));
            }
        }

        for (Object property : PropertyKeys.KEYS) {
            if (!isIn(properties.keySet(), property)) {
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

    private static Set<String> getDuplicatedProperties(final Set<Object> engineProperties, final Set<Object> applicationProperties) {
        return applicationProperties.stream().filter(engineProperties::contains).map(Object::toString).collect(Collectors.toSet());
    }

    private static Properties loadProperties(final String file) {
        Properties properties = new Properties();
        try (InputStream inputStream = resourceLoader.getUrl(file).openStream()) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PIEngineException("Could not find property file %s!", file);
        }

        return properties;
    }

}
