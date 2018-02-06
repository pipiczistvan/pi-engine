package piengine.visual.texture.image.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.texture.Texture;
import piengine.io.loader.png.domain.PngDto;
import piengine.io.loader.png.loader.PngLoader;
import piengine.visual.texture.image.domain.Image;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

@Component
public class ImageService extends SupplierService<String, Image> {

    private final PngLoader pngLoader;
    private final Map<String, Image> imageMap;

    @Wire
    public ImageService(final PngLoader pngLoader) {
        this.pngLoader = pngLoader;
        this.imageMap = new HashMap<>();
    }

    @Override
    public Image supply(final String key) {
        return imageMap.computeIfAbsent(key, this::createImage);
    }

    @Override
    public void terminate() {
        imageMap.values().forEach(Image::clear);
    }

    private Image createImage(final String key) {
        PngDto pngDto = pngLoader.load(key);

        Texture texture = new Image(pngDto.width, pngDto.height)
                .bind(GL_TEXTURE0);

        if (pngDto.comp == 3) {
            texture.pixelStore(pngDto.width)
                    .attachBuffer(GL_RGB8, GL_UNSIGNED_BYTE, pngDto.buffer);
        } else {
            texture.attachBuffer(GL_RGBA8, GL_UNSIGNED_BYTE, pngDto.buffer)
                    .attachBlendFunc();
        }

        return (Image) texture
                .generateMipmap(GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST)
                .unbind();
    }
}
