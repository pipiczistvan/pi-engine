package piengine.visual.framebuffer.interpreter;

import org.joml.Vector2i;
import piengine.core.base.api.Interpreter;
import piengine.core.base.exception.PIEngineException;
import piengine.visual.framebuffer.domain.FramebufferAttachment;
import piengine.visual.framebuffer.domain.FramebufferDao;
import piengine.visual.framebuffer.domain.FramebufferData;
import puppeteer.annotation.premade.Component;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.RENDER_BUFFER_ATTACHMENT;

@Component
public class FramebufferInterpreter implements Interpreter<FramebufferData, FramebufferDao> {

    @Override
    public FramebufferDao create(final FramebufferData framebufferData) {
        int drawBuffer = framebufferData.drawingEnabled ? GL_COLOR_ATTACHMENT0 : GL_NONE;
        int fbo = createFrameBuffer(drawBuffer);

        Map<FramebufferAttachment, Integer> attachments = new HashMap<>();
        for (FramebufferAttachment attachment : framebufferData.attachments) {
            attachments.put(attachment, createAttachment(attachment, framebufferData.resolution));
        }

        unbind();

        return new FramebufferDao(fbo, attachments, framebufferData.textureAttachment);
    }

    @Override
    public void free(final FramebufferDao dao) {
        glDeleteFramebuffers(dao.getFbo());

        for (Map.Entry<FramebufferAttachment, Integer> attachment : dao.getAttachments().entrySet()) {
            if (attachment.getKey().equals(RENDER_BUFFER_ATTACHMENT)) {
                glDeleteRenderbuffers(attachment.getValue());
            } else {
                glDeleteTextures(attachment.getValue());
            }
        }
    }

    public void bind(final FramebufferDao dao) {
        glBindFramebuffer(GL_FRAMEBUFFER, dao.getFbo());
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private int createFrameBuffer(final int buffer) {
        int frameBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
        glDrawBuffer(buffer);
        glReadBuffer(buffer);

        return frameBuffer;
    }

    private int createAttachment(final FramebufferAttachment attachment, final Vector2i viewport) {
        switch (attachment) {
            case COLOR_ATTACHMENT:
                return createColorAttachment(viewport);
            case DEPTH_TEXTURE_ATTACHMENT:
                return createDepthTextureAttachment(viewport);
            case RENDER_BUFFER_ATTACHMENT:
                return createRenderBufferAttachment(viewport);
            default:
                throw new PIEngineException("Invalid framebuffer attachment: %s", attachment.name());
        }
    }

    private int createColorAttachment(final Vector2i viewport) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, viewport.x, viewport.y, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);

        return texture;
    }

    private int createDepthTextureAttachment(final Vector2i viewport) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, viewport.x, viewport.y, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, texture, 0);

        return texture;
    }

    private int createRenderBufferAttachment(final Vector2i viewPort) {
        int rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT24, viewPort.x, viewPort.y);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);

        return rbo;
    }
}
