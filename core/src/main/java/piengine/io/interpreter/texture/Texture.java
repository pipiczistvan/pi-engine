package piengine.io.interpreter.texture;

import piengine.io.interpreter.Interpreter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture implements Interpreter {

    public final int id;
    public final int width;
    public final int height;

    public Texture(final int width, final int height) {
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
    }

    public Texture bind(final int bank) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_2D, id);

        return this;
    }

    public Texture unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);

        return this;
    }

    public Texture attachBuffer(final int format, final int type, final ByteBuffer buffer) {
        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, type, buffer);

        return this;
    }

    public Texture attachBlendFunc() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        return this;
    }

    public Texture generateMipmap(final int minFilter, final int magFilter) {
        attachParameter(GL_TEXTURE_MIN_FILTER, minFilter);
        attachParameter(GL_TEXTURE_MAG_FILTER, magFilter);
        glGenerateMipmap(GL_TEXTURE_2D);

        return this;
    }

    public Texture attachParameter(final int pname, final int param) {
        glTexParameteri(GL_TEXTURE_2D, pname, param);

        return this;
    }

    public Texture pixelStore(final int width) {
        if ((width & 3) != 0) {
            glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1));
        }

        return this;
    }

    @Override
    public void clear() {
        unbind();
        glDeleteTextures(id);
    }

    public static void bindMinus(final int bank) {
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_2D, -1);
    }
}
