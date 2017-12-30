package piengine.visual.shader.accessor;

import org.apache.log4j.Logger;
import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.shader.domain.ShaderData;
import puppeteer.annotation.premade.Component;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.SHADERS_LOCATION;

@Component
public class ShaderAccessor implements Accessor<ShaderData> {

    private static final String ROOT = get(SHADERS_LOCATION);
    private static final String SHADER_EXT = "glsl";

    private final Logger logger = Logger.getLogger(getClass());

    private final ResourceLoader loader;

    public ShaderAccessor() {
        this.loader = new ResourceLoader(ROOT, SHADER_EXT);
    }

    @Override
    public ShaderData access(String file) {
        final String vertexSource = tryToReadFile("vertex", file);
        final String tessControlSource = tryToReadFile("tessellation/control", file);
        final String tessEvalSource = tryToReadFile("tessellation/evaluation", file);
        final String geometrySource = tryToReadFile("geometry", file);
        final String fragmentSource = tryToReadFile("fragment", file);
        return new ShaderData(vertexSource, tessControlSource, tessEvalSource, geometrySource, fragmentSource);
    }

    private String tryToReadFile(final String shaderType, final String shaderName) {
        String file = String.format("%s/%s", shaderType, shaderName);

        try {
            return loader.load(file);
        } catch (PIEngineException e) {
            logger.warn("Could not load shader file!", e);
        }

        return null;
    }

}
