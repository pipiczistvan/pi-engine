package piengine.visual.texture.service;

import piengine.core.base.resource.SupplierService;
import piengine.visual.texture.accessor.TextureAccessor;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.domain.TextureDao;
import piengine.visual.texture.domain.TextureData;
import piengine.visual.texture.interpreter.TextureInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class TextureService extends SupplierService<Texture, TextureDao, TextureData> {

    private TextureInterpreter textureInterpreter;

    @Wire
    public TextureService(final TextureAccessor textureAccessor, final TextureInterpreter textureInterpreter) {
        super(textureAccessor, textureInterpreter);
        this.textureInterpreter = textureInterpreter;
    }

    @Override
    protected Texture createDomain(final TextureDao dao, final TextureData resource) {
        return new Texture(dao);
    }

    public void bind(final Texture texture) {
        textureInterpreter.bind(texture.dao);
    }

}
