package piengine.visual.texture.manager;

import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextureManager {

    private final TextureService textureService;

    @Wire
    public TextureManager(final TextureService textureService) {
        this.textureService = textureService;
    }

    public Texture supply(final String file) {
        return textureService.supply(file);
    }

}
