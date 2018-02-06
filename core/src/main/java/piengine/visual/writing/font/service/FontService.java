package piengine.visual.writing.font.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.loader.fnt.FontDto;
import piengine.io.loader.fnt.FontLoader;
import piengine.io.loader.png.domain.PngDto;
import piengine.io.loader.png.loader.PngLoader;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.domain.FontKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;

@Component
public class FontService extends SupplierService<FontKey, Font> {

    private final FontLoader fontLoader;
    private final PngLoader pngLoader;
    private final Map<String, Font> fontMap;

    @Wire
    public FontService(final FontLoader fontLoader, final PngLoader pngLoader) {
        this.fontLoader = fontLoader;
        this.pngLoader = pngLoader;
        this.fontMap = new HashMap<>();
    }

    @Override
    public Font supply(final FontKey key) {
        return fontMap.computeIfAbsent(key.fontFile, k -> createFont(key));
    }

    @Override
    public void terminate() {
        fontMap.values().forEach(Font::clear);
    }

    private Font createFont(final FontKey key) {
        FontDto font = fontLoader.load(key);
        PngDto png = pngLoader.load(key.fontFile);

        return (Font) new Font(png.width, png.height, font)
                .bind(GL_TEXTURE0)
                .attachBuffer(GL_RGBA8, GL_UNSIGNED_BYTE, png.buffer)
                .attachBlendFunc()
                .attachParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
                .attachParameter(GL_TEXTURE_LOD_BIAS, 0)
                .unbind();
    }
}
