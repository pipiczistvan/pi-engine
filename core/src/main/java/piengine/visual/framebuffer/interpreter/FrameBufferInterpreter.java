package piengine.visual.framebuffer.interpreter;

import org.joml.Vector2i;
import piengine.core.base.api.Interpreter;
import piengine.visual.framebuffer.domain.FrameBufferDao;
import piengine.visual.framebuffer.domain.FrameBufferData;
import puppeteer.annotation.premade.Component;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

@Component
public class FrameBufferInterpreter implements Interpreter<FrameBufferDao, FrameBufferData> {

    @Override
    public FrameBufferDao create(final FrameBufferData frameBufferData) {
        int fbo = createFrameBuffer();
        int texture = createTextureAttachment(frameBufferData.viewport);
        int rbo = createRenderBuffer(frameBufferData.viewport);

        unbind();

        return new FrameBufferDao(fbo, texture, rbo);
    }

    @Override
    public void free(final FrameBufferDao dao) {
        glDeleteFramebuffers(dao.fbo);
        glDeleteTextures(dao.texture);
        glDeleteRenderbuffers(dao.rbo);
    }

    public void bind(final FrameBufferDao dao) {
        glBindFramebuffer(GL_FRAMEBUFFER, dao.fbo);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private int createFrameBuffer() {
        int frameBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
        glDrawBuffer(GL_COLOR_ATTACHMENT0);

        return frameBuffer;
    }

    private int createTextureAttachment(final Vector2i viewPort) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, viewPort.x, viewPort.y, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);

        return texture;
    }

    private int createRenderBuffer(final Vector2i viewPort) {
        int rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, viewPort.x, viewPort.y);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);

        return rbo;
    }
}
