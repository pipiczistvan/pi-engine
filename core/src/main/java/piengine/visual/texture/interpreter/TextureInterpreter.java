package piengine.visual.texture.interpreter;

import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;

@Component
public class TextureInterpreter {

    public void bindTexture(final int bank, final int texture) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void bindCubemap(final int bank, final int texture) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
    }
}
