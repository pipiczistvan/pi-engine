package piengine.visual.shader.accessor;

import org.apache.log4j.Logger;
import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.utils.StringUtils;
import piengine.visual.shader.domain.ShaderData;
import piengine.visual.shader.domain.ShaderKey;
import puppeteer.annotation.premade.Component;

import java.util.List;
import java.util.regex.Pattern;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.SHADERS_LOCATION;

@Component
public class ShaderAccessor implements Accessor<ShaderKey, ShaderData> {

    private static final String ROOT = get(SHADERS_LOCATION);
    private static final String SHADER_EXT = "glsl";
    private static Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\{.+}");
    private final Logger logger = Logger.getLogger(getClass());

    private final ResourceLoader loader;

    public ShaderAccessor() {
        this.loader = new ResourceLoader(ROOT, SHADER_EXT);
    }

    @Override
    public ShaderData access(final ShaderKey key) {
        final String vertexSource = tryToReadFile("vertex", key.file);
        final String tessControlSource = tryToReadFile("tessellation/control", key.file);
        final String tessEvalSource = tryToReadFile("tessellation/evaluation", key.file);
        final String geometrySource = tryToReadFile("geometry", key.file);
        final String fragmentSource = tryToReadFile("fragment", key.file);
        return new ShaderData(vertexSource, tessControlSource, tessEvalSource, geometrySource, fragmentSource);
    }

    private String tryToReadFile(final String shaderType, final String shaderName) {
        String file = String.format("%s/%s", shaderType, shaderName);

        try {
            String rawShader = loader.load(file);
            List<String> variables = StringUtils.findAllOccurrences(VARIABLE_PATTERN, rawShader);
            for (String variable : variables) {
                String key = variable.substring(2, variable.length() - 1);
                String value = get(key);

                rawShader = rawShader.replace(variable, value);
            }

            return rawShader;
        } catch (PIEngineException e) {
            logger.warn("Could not load shader file!", e);
        }

        return null;
    }

}
