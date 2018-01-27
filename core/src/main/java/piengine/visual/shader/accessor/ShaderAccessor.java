package piengine.visual.shader.accessor;

import org.apache.log4j.Logger;
import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.utils.StringUtils;
import piengine.visual.shader.domain.ShaderData;
import piengine.visual.shader.domain.ShaderKey;
import puppeteer.annotation.premade.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.SHADERS_LOCATION;

@Component
public class ShaderAccessor implements Accessor<ShaderKey, ShaderData> {

    private static final String ROOT = get(SHADERS_LOCATION);
    private static final String SHADER_EXT = "glsl";
    private static Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{.+}");
    private static Pattern IMPORT_PATTERN = Pattern.compile("#import \".+\";");
    private final Logger logger = Logger.getLogger(getClass());

    private final ResourceLoader loader;

    public ShaderAccessor() {
        this.loader = new ResourceLoader(ROOT, SHADER_EXT);
    }

    @Override
    public ShaderData access(final ShaderKey key) {
        final String vertexSource = tryToReadShader("vertex", key.file);
        final String tessControlSource = tryToReadShader("tessellation/control", key.file);
        final String tessEvalSource = tryToReadShader("tessellation/evaluation", key.file);
        final String geometrySource = tryToReadShader("geometry", key.file);
        final String fragmentSource = tryToReadShader("fragment", key.file);

        return new ShaderData(vertexSource, tessControlSource, tessEvalSource, geometrySource, fragmentSource);
    }

    private String tryToReadShader(final String shaderType, final String shaderName) {
        try {
            String shaderPath = String.format("%s/%s", shaderType, shaderName);
            Set<String> processedImports = new HashSet<>();

            return readFile("pipeline", shaderPath, processedImports);
        } catch (PIEngineException e) {
            logger.warn("Could not load shader file!", e);
            return null;
        }
    }

    private String readFile(final String folder, String path, final Set<String> processedImports) {
        String file = String.format("%s/%s", folder, path);

        String rawShader = loader.load(file);
        rawShader = replaceImports(rawShader, processedImports);
        rawShader = replaceVariables(rawShader);

        return rawShader;
    }

    private String replaceImports(final String source, final Set<String> processedImports) {
        String processed = source;

        List<String> foundImports = StringUtils.findAllOccurrences(IMPORT_PATTERN, source);
        for (String foundImport : foundImports) {
            if (!processedImports.contains(foundImport)) {
                String importReference = foundImport.substring(9, foundImport.length() - 2);

                String importSource = readFile("common", importReference, processedImports);
                if (importSource == null) {
                    throw new PIEngineException("Could not find import reference %s!", importReference);
                }
                processed = processed.replaceFirst(foundImport, importSource);
                processedImports.add(foundImport);
            } else {
                processed = processed.replaceFirst(foundImport, "");
            }
        }


        return processed;
    }

    private String replaceVariables(final String source) {
        String processed = source;
        List<String> variables = StringUtils.findAllOccurrences(VARIABLE_PATTERN, source);
        for (String variable : variables) {
            String key = variable.substring(2, variable.length() - 1);
            String value = get(key);

            processed = processed.replace(variable, value);
        }

        return processed;
    }
}
