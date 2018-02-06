package piengine.visual.texture.sprite.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.texture.Texture;
import piengine.io.loader.png.domain.PngDto;
import piengine.io.loader.png.loader.PngLoader;
import piengine.visual.texture.sprite.domain.Sprite;
import piengine.visual.texture.sprite.domain.SpriteKey;
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
public class SpriteService extends SupplierService<SpriteKey, Sprite> {

    private final PngLoader pngLoader;
    private final Map<SpriteKey, Sprite> spriteMap;

    @Wire
    public SpriteService(final PngLoader pngLoader) {
        this.pngLoader = pngLoader;
        this.spriteMap = new HashMap<>();
    }

    @Override
    public Sprite supply(final SpriteKey key) {
        return spriteMap.computeIfAbsent(key, this::createSprite);
    }

    @Override
    public void terminate() {
        spriteMap.values().forEach(Sprite::clear);
    }

    private Sprite createSprite(final SpriteKey key) {
        PngDto pngDto = pngLoader.load(key.file);

        Texture texture = new Sprite(pngDto.width, pngDto.height, key.numberOfRows)
                .bind(GL_TEXTURE0);

        if (pngDto.comp == 3) {
            texture.pixelStore(pngDto.width)
                    .attachBuffer(GL_RGB8, GL_UNSIGNED_BYTE, pngDto.buffer);
        } else {
            texture.attachBuffer(GL_RGBA8, GL_UNSIGNED_BYTE, pngDto.buffer)
                    .attachBlendFunc();
        }

        return (Sprite) texture
                .generateMipmap(GL_NEAREST_MIPMAP_NEAREST, GL_NEAREST)
                .unbind();
    }
}
