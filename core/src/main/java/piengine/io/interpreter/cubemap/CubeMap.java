package piengine.io.interpreter.cubemap;

import piengine.io.interpreter.Interpreter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class CubeMap implements Interpreter {

    public final int id;

    public CubeMap() {
        this.id = glGenTextures();
    }

    public CubeMap bind(final int bank) {
        glEnable(GL_TEXTURE_CUBE_MAP);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_CUBE_MAP, id);

        return this;
    }

    public CubeMap unbind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);

        return this;
    }

    public CubeMap attachBuffer(final int target, final int format, final int type, final int width, final int height, final ByteBuffer buffer) {
        glTexImage2D(target, 0, format, width, height, 0, format, type, buffer);

        return this;
    }

    public CubeMap attachParameter(final int pname, final int param) {
        glTexParameteri(GL_TEXTURE_CUBE_MAP, pname, param);

        return this;
    }

    @Override
    public void clear() {
        glDeleteTextures(id);
    }

    public static void bindMinus(final int bank) {
        glEnable(GL_TEXTURE_CUBE_MAP);
        glActiveTexture(bank);
        glBindTexture(GL_TEXTURE_CUBE_MAP, -1);
    }
}
