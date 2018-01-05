package piengine.visual.framebuffer.interpreter;

import org.joml.Vector2i;
import piengine.core.base.api.Interpreter;
import piengine.visual.framebuffer.domain.FrameBufferDao;
import piengine.visual.framebuffer.domain.FrameBufferData;
import puppeteer.annotation.premade.Component;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

@Component
public class FrameBufferInterpreter implements Interpreter<FrameBufferData, FrameBufferDao> {

    @Override
    public FrameBufferDao create(final FrameBufferData frameBufferData) {
        int fbo = createFrameBuffer();
        int rbo = createRenderBuffer(frameBufferData.viewport);

        int[] textures = new int[frameBufferData.attachments.length];
        for (int i = 0; i < frameBufferData.attachments.length; i++) {
            switch (frameBufferData.attachments[i]) {
                case COLOR_TEXTURE_ATTACHMENT:
                    textures[i] = createColorTextureAttachment(frameBufferData.viewport);
                    break;
                case DEPTH_TEXTURE_ATTACHMENT:
                    textures[i] = createDepthTextureAttachment(frameBufferData.viewport);
                    break;
            }
        }

        unbind();

        return new FrameBufferDao(fbo, rbo, textures);
    }

    @Override
    public void free(final FrameBufferDao dao) {
        glDeleteFramebuffers(dao.fbo);
        glDeleteRenderbuffers(dao.rbo);
        for (int texture : dao.textures) {
            glDeleteTextures(texture);
        }
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

    private int createColorTextureAttachment(final Vector2i viewport) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, viewport.x, viewport.y, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);

        return texture;
    }

    private int createDepthTextureAttachment(final Vector2i viewport) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, viewport.x, viewport.y, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture, 0);
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
