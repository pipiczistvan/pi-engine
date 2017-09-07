package piengine.visual.texture.accessor;

import org.lwjgl.BufferUtils;
import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.visual.texture.domain.TextureData;
import puppeteer.annotation.premade.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static piengine.core.base.resource.ResourceLoader.createFilePath;
import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.TEXTURES_LOCATION;
import static piengine.core.utils.IOUtils.ioResourceToByteBuffer;

@Component
public class TextureAccessor implements Accessor<TextureData> {

    private static final String ROOT = get(TEXTURES_LOCATION);
    private static final String PNG_EXT = "png";
    private static final int BUFFER_LIMIT = 1024 * 8;

    @Override
    public TextureData access(final String file) {
        final String fullPath = createFilePath(ROOT, file, PNG_EXT);

        ByteBuffer imageBuffer;
        try {
            imageBuffer = ioResourceToByteBuffer(fullPath, BUFFER_LIMIT);
        } catch (IOException e) {
            throw new PIEngineException("Could not load image %s!", fullPath, e);
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

        return new TextureData(image, w.get(), h.get(), comp.get());
    }

    @Override
    public void free(final TextureData resource) {
        stbi_image_free(resource.buffer);
    }

}
