package piengine.visual.writing.font.interpreter;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import piengine.core.base.api.Interpreter;
import piengine.visual.image.accessor.ImageAccessor;
import piengine.visual.image.domain.ImageData;
import piengine.visual.writing.font.domain.FontDao;
import piengine.visual.writing.font.domain.FontData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_RGBA8;

@Component
public class FontInterpreter extends Interpreter<FontData, FontDao> {

    private final ImageAccessor imageAccessor;

    @Wire
    public FontInterpreter(final ImageAccessor imageAccessor) {
        this.imageAccessor = imageAccessor;
    }

    @Override
    protected FontDao createDao(final FontData fontData) {
        ImageData imageData = imageAccessor.access(fontData.imageKey);

        FontDao dao = new FontDao(GL11.glGenTextures());
        bind(dao);

        if (imageData.comp == 3) {
            if ((imageData.width & 3) != 0) {
                GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 2 - (imageData.width & 1));
            }
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL_RGB8, imageData.width, imageData.height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, imageData.buffer);
        } else {
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL_RGBA8, imageData.width, imageData.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData.buffer);

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
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, dao.getTexture());
    }

    @Override
    protected boolean freeDao(final FontDao dao) {
        GL11.glDeleteTextures(dao.getTexture());

        return true;
    }

    @Override
    protected String getCreateInfo(final FontDao dao, final FontData resource) {
        return String.format("Texture id: %s", dao.getTexture());
    }

    @Override
    protected String getFreeInfo(final FontDao dao) {
        return String.format("Texture id: %s", dao.getTexture());
    }
}
