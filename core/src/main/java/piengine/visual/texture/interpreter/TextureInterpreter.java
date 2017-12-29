package piengine.visual.texture.interpreter;

import piengine.visual.texture.domain.TextureDao;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

@Component
public class TextureInterpreter {

    public void bind(final TextureDao dao) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, dao.texture);
    }

}
