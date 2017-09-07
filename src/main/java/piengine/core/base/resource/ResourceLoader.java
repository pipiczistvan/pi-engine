package piengine.core.base.resource;

import piengine.core.base.exception.PIEngineException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ResourceLoader {

    private final String root;
    private final String extension;

    public ResourceLoader(final String root, final String extension) {
        this.root = root;
        this.extension = extension;
    }

    public String load(final String file) {
        String fullPath = createFilePath(root, file, extension);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            throw new PIEngineException("Could not find file %s!", fullPath, e);
        }

        return builder.toString();
    }

    public static String createFilePath(final String root, final String file, final String extension) {
        return String.format("%s%s.%s", root, file, extension);
    }

}
