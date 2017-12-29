package piengine.visual.image.accessor;

import org.lwjgl.BufferUtils;
import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.image.domain.ImageData;
import puppeteer.annotation.premade.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.stb.STBImage.*;
import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.IMAGES_LOCATION;

@Component
public class ImageAccessor implements Accessor<ImageData> {

    private static final String ROOT = get(IMAGES_LOCATION);
    private static final String PNG_EXT = "png";
    private static final int BUFFER_LIMIT = 1024 * 8;

    private final ResourceLoader loader;

    public ImageAccessor() {
        this.loader = new ResourceLoader(ROOT, PNG_EXT);
    }

    @Override
    public ImageData access(final String file) {
        ByteBuffer imageBuffer;
        try {
            imageBuffer = loader.loadByteBuffer(file, BUFFER_LIMIT);
        } catch (IOException | URISyntaxException e) {
            throw new PIEngineException("Could not load image %s!", file, e);
        }

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        if (stbi_info_from_memory(imageBuffer, w, h, comp) == GL_FALSE) {
            throw new PIEngineException("Failed to read image information %s!", stbi_failure_reason());
        }

        ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
        if (image == null) {
            throw new PIEngineException("Failed to load image %s!", stbi_failure_reason());
        }

        return new ImageData(image, w.get(), h.get(), comp.get());
    }

    @Override
    public void free(final ImageData resource) {
        stbi_image_free(resource.buffer);
    }
}
