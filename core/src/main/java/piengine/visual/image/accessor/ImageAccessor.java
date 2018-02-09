package piengine.visual.image.accessor;

import org.lwjgl.BufferUtils;
import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.image.domain.ImageData;
import piengine.visual.image.domain.ImageKey;
import puppeteer.annotation.premade.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

@Component
public class ImageAccessor extends Accessor<ImageKey, ImageData> {

    private static final String ROOT = get(IMAGES_LOCATION);
    private static final String PNG_EXT = "png";
    private static final int BUFFER_LIMIT = 1024 * 8;

    private final ResourceLoader loader;

    public ImageAccessor() {
        this.loader = new ResourceLoader(ROOT, PNG_EXT);
    }

    @Override
    protected ImageData accessResource(final ImageKey key) {
        ByteBuffer imageBuffer;
        try {
            imageBuffer = loader.loadByteBuffer(key.file, BUFFER_LIMIT);
        } catch (IOException | URISyntaxException e) {
            throw new PIEngineException("Could not load image %s!", key.file, e);
        }

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
            throw new PIEngineException("Failed to read image information %s!", stbi_failure_reason());
        }

        ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
        if (image == null) {
            throw new PIEngineException("Failed to load image %s!", stbi_failure_reason());
        }

        return new ImageData(image, w.get(), h.get(), comp.get());
    }

    @Override
    protected boolean freeResource(final ImageData resource) {
        stbi_image_free(resource.buffer);

        return true;
    }

    @Override
    protected String getAccessInfo(final ImageKey key, final ImageData resource) {
        return String.format("File: %s", key.file);
    }
}
