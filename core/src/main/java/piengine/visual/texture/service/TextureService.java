package piengine.visual.texture.service;

import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.domain.TextureDao;
import piengine.visual.texture.interpreter.TextureInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextureService {

    private TextureInterpreter textureInterpreter;

    @Wire
    public TextureService(final TextureInterpreter textureInterpreter) {
        this.textureInterpreter = textureInterpreter;
    }

    public <T extends TextureDao> void bind(final Texture<T> texture) {
        textureInterpreter.bind(texture.dao);
    }

}
