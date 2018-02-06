package piengine.io.interpreter.framebuffer;

import piengine.io.interpreter.Interpreter;

import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.glRenderbufferStorageMultisample;

public class Renderbuffer implements Interpreter {

    public final int id;
    public final int width;
    public final int height;

    public Renderbuffer(final int width, final int height) {
        this.id = glGenRenderbuffers();
        this.width = width;
        this.height = height;
    }

    public Renderbuffer bind() {
        glBindRenderbuffer(GL_RENDERBUFFER, id);

        return this;
    }

    public Renderbuffer unbind() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);

        return this;
    }

    @Override
    public void clear() {
        glDeleteRenderbuffers(id);
    }

    public Renderbuffer attachStorage(final int format) {
        glRenderbufferStorage(GL_RENDERBUFFER, format, width, height);

        return this;
    }

    public Renderbuffer attachStorage(final int format, final int samples) {
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, format, width, height);

        return this;
    }
}
