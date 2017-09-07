package piengine.visual.shader.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.shader.domain.ShaderData;
import puppeteer.annotation.premade.Component;

import java.io.File;

import static piengine.core.base.resource.ResourceLoader.createFilePath;
import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.SHADERS_LOCATION;

@Component
public class ShaderAccessor implements Accessor<ShaderData> {

    private static final String ROOT = get(SHADERS_LOCATION);
    private static final String SHADER_EXT = "glsl";

    private final ResourceLoader loader;

    public ShaderAccessor() {
        this.loader = new ResourceLoader(ROOT, SHADER_EXT);
    }

    @Override
    public ShaderData access(String file) {
        final String vertexSource = tryToReadFile("vertex", file);
        final String geometrySource = tryToReadFile("geometry", file);
        final String fragmentSource = tryToReadFile("fragment", file);
        return new ShaderData(vertexSource, geometrySource, fragmentSource);
    }

    private String tryToReadFile(final String shaderType, final String shaderName) {
        String file = String.format("%s/%s", shaderType, shaderName);
        String filePath = createFilePath(ROOT, file, SHADER_EXT);

        File shaderFile = new File(filePath);
        if (shaderFile.exists()) {
            return loader.load(file);
        }
        return null;
    }

}
