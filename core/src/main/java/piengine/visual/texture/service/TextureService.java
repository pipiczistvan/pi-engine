package piengine.visual.texture.service;

import piengine.visual.texture.domain.Texture;
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

    public void bind(final Texture texture) {
        bind(GL_TEXTURE0, texture);
    }

    public void bind(final int textureId) {
        bind(GL_TEXTURE0, textureId);
    }

    public void bind(final int textureBank, final Texture texture) {
        bind(textureBank, texture.getDao().getTexture());
    }

    public void bind(final int textureBank, final int textureId) {
        textureInterpreter.bindTexture(textureBank, textureId);
    }

    public void bindCubeMap(final Texture texture) {
        bindCubeMap(GL_TEXTURE0, texture);
    }

    public void bindCubeMap(final int textureBank, final Texture texture) {
        bindCubeMap(textureBank, texture.getDao().getTexture());
    }

    public void bindCubeMap(final int textureBank, final int textureId) {
        textureInterpreter.bindCubeMap(textureBank, textureId);
    }
}
