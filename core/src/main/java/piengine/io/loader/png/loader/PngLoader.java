package piengine.io.loader.png.loader;

import org.lwjgl.BufferUtils;
import piengine.core.base.api.Service;
import piengine.core.base.api.Terminatable;
import piengine.core.base.exception.PIEngineException;
import piengine.io.loader.AbstractLoader;
import piengine.io.loader.ResourceLoader;
import piengine.io.loader.png.domain.PngDto;
import puppeteer.annotation.premade.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

@Component
public class PngLoader extends AbstractLoader<String, PngDto> implements Service, Terminatable {

    private static final String ROOT = get(IMAGES_LOCATION);
    private static final String PNG_EXT = "png";
    private static final int BUFFER_LIMIT = 1024 * 8;

    private final ResourceLoader loader;

    public PngLoader() {
        this.loader = new ResourceLoader(ROOT, PNG_EXT);
    }

    @Override
    protected PngDto createDto(final String key) {
        ByteBuffer imageBuffer;
        try {
            imageBuffer = loader.loadByteBuffer(key, BUFFER_LIMIT);
        } catch (IOException | URISyntaxException e) {
            throw new PIEngineException("Could not load png %s!", key, e);
        }

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        if (stbi_info_from_memory(imageBuffer, w, h, comp) == GL_FALSE) {
            throw new PIEngineException("Failed to read png information %s!", stbi_failure_reason());
        }

        ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
        if (image == null) {
            throw new PIEngineException("Failed to load png %s!", stbi_failure_reason());
        }

        return new PngDto(imageBuffer, w.get(), h.get(), comp.get());
    }

    @Override
    public void terminate() {
//        loadMap.values().forEach(png -> stbi_image_free(png.buffer));
    }
}
