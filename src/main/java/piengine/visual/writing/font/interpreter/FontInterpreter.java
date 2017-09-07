package piengine.visual.writing.font.interpreter;

import org.lwjgl.opengl.GL14;
import piengine.core.base.api.Interpreter;
import piengine.visual.texture.accessor.TextureAccessor;
import piengine.visual.texture.domain.TextureData;
import piengine.visual.writing.font.domain.FontDao;
import piengine.visual.writing.font.domain.FontData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Component
public class FontInterpreter implements Interpreter<FontDao, FontData> {

    private final TextureAccessor textureAccessor;

    @Wire
    public FontInterpreter(final TextureAccessor textureAccessor) {
        this.textureAccessor = textureAccessor;
    }

    @Override
    public FontDao create(FontData fontData) {
        TextureData textureData = textureAccessor.access(fontData.file);

        FontDao dao = new FontDao(glGenTextures());
        bind(dao);

        if (textureData.comp == 3) {
            if ((textureData.width & 3) != 0) {
                glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (textureData.width & 1));
            }
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, textureData.width, textureData.height, 0, GL_RGB, GL_UNSIGNED_BYTE, textureData.buffer);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureData.width, textureData.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData.buffer);

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
        glBindTexture(GL_TEXTURE_2D, 0);

        return dao;
    }

    public void bind(final FontDao dao) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, dao.id);
    }

    @Override
    public void free(final FontDao dao) {
        glDeleteTextures(dao.id);
    }

}
