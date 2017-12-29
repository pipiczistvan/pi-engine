package piengine.visual.image.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.image.domain.ImageDao;
import piengine.visual.image.domain.ImageData;
import piengine.visual.texture.domain.TextureDao;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Component
public class ImageInterpreter implements Interpreter<ImageDao, ImageData> {

    @Override
    public ImageDao create(final ImageData imageData) {
        ImageDao dao = new ImageDao(glGenTextures());
        bind(dao);

        if (imageData.comp == 3) {
            if ((imageData.width & 3) != 0) {
                glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (imageData.width & 1));
            }
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, imageData.width, imageData.height, 0, GL_RGB, GL_UNSIGNED_BYTE, imageData.buffer);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, imageData.width, imageData.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData.buffer);

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);

        return dao;
    }

    @Override
    public void free(final ImageDao dao) {
        glDeleteTextures(dao.texture);
    }

    private void bind(final TextureDao dao) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, dao.texture);
    }
}
