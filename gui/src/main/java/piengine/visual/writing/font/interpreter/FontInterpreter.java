package piengine.visual.writing.font.interpreter;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import piengine.common.gui.writing.font.domain.FontDao;
import piengine.common.gui.writing.font.domain.FontData;
import piengine.core.base.api.Interpreter;
import piengine.visual.texture.accessor.TextureAccessor;
import piengine.visual.texture.domain.TextureData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

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

        FontDao dao = new FontDao(GL11.glGenTextures());
        bind(dao);

        if (textureData.comp == 3) {
            if ((textureData.width & 3) != 0) {
                GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 2 - (textureData.width & 1));
            }
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, textureData.width, textureData.height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, textureData.buffer);
        } else {
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, textureData.width, textureData.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, textureData.buffer);

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return dao;
    }

    public void bind(final FontDao dao) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, dao.id);
    }

    @Override
    public void free(final FontDao dao) {
        GL11.glDeleteTextures(dao.id);
    }

}
