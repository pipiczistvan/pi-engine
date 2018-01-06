package piengine.visual.texture.interpreter;

import piengine.visual.texture.domain.TextureDao;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.glActiveTexture;

@Component
public class TextureInterpreter {

    public void bind(final int bank, final TextureDao dao) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_2D, dao.getTexture());
    }

}
