package piengine.visual.texture.service;

import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.domain.TextureDao;
import piengine.visual.texture.interpreter.TextureInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

@Component
public class TextureService {

    private TextureInterpreter textureInterpreter;

    @Wire
    public TextureService(final TextureInterpreter textureInterpreter) {
        this.textureInterpreter = textureInterpreter;
    }

    public <T extends TextureDao> void bind(final Texture<T> texture) {
        bind(GL_TEXTURE0, texture);
    }

    public <T extends TextureDao> void bind(final int textureBank, final Texture<T> texture) {
        bind(textureBank, texture.getDao().getTexture());
    }

    public void bind(final int textureBank, final int textureId) {
        textureInterpreter.bindTexture(textureBank, textureId);
    }

    public <T extends TextureDao> void bindCubeMap(final Texture<T> texture) {
        bindCubeMap(GL_TEXTURE0, texture);
    }

    public <T extends TextureDao> void bindCubeMap(final int textureBank, final Texture<T> texture) {
        bindCubeMap(textureBank, texture.getDao().getTexture());
    }

    public void bindCubeMap(final int textureBank, final int textureId) {
        textureInterpreter.bindCubemap(textureBank, textureId);
    }
}
