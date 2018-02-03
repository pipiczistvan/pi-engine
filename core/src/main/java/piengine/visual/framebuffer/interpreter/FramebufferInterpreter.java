package piengine.visual.framebuffer.interpreter;

import org.joml.Vector2i;
import piengine.core.base.api.Interpreter;
import piengine.core.base.exception.PIEngineException;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferAttachment;
import piengine.visual.framebuffer.domain.FramebufferDao;
import piengine.visual.framebuffer.domain.FramebufferData;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Component;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
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
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glDeleteRenderbuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.glRenderbufferStorageMultisample;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT;

@Component
public class FramebufferInterpreter implements Interpreter<FramebufferData, FramebufferDao> {

    private static final int SAMPLES = 4;

    @Override
    public FramebufferDao create(final FramebufferData framebufferData) {
        int drawBuffer = framebufferData.drawingEnabled ? GL_COLOR_ATTACHMENT0 : GL_NONE;
        int fbo = createFrameBuffer(drawBuffer);

        Map<FramebufferAttachment, Integer> attachments = new HashMap<>();
        for (FramebufferAttachment attachment : framebufferData.attachments) {
            attachments.put(attachment, createAttachment(attachment, framebufferData.resolution, framebufferData.texture));
        }

        unbind();

        return new FramebufferDao(fbo, attachments, framebufferData.textureAttachment);
    }

    @Override
    public void free(final FramebufferDao dao) {
        unbind();
        glDeleteFramebuffers(dao.getFbo());

        for (Map.Entry<FramebufferAttachment, Integer> attachment : dao.getAttachments().entrySet()) {
            if (attachment.getKey().equals(DEPTH_BUFFER_ATTACHMENT)
                    || attachment.getKey().equals(DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT)
                    || attachment.getKey().equals(COLOR_BUFFER_MULTISAMPLE_ATTACHMENT)) {
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

    public void blit(final Framebuffer src, final Framebuffer dest) {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, dest.getDao().getFbo());
        glBindFramebuffer(GL_READ_FRAMEBUFFER, src.getDao().getFbo());
        glBlitFramebuffer(
                0, 0, src.getSize().x, src.getSize().y,
                0, 0, dest.getSize().x, dest.getSize().y,
                GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT, GL_NEAREST);
        unbind();
    }

    private int createFrameBuffer(final int buffer) {
        int frameBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);
        glDrawBuffer(buffer);
        glReadBuffer(buffer);

        return frameBuffer;
    }

    private int createColorTextureAttachment(final Vector2i resolution, final Texture texture) {
        int textureId = texture != null ? texture.getDao().getTexture() :
                generateTexture(resolution, GL_RGB, GL_RGB, GL_UNSIGNED_BYTE);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, textureId, 0);

        return textureId;
    }

    private int createDepthTextureAttachment(final Vector2i resolution, final Texture texture) {
        int textureId = texture != null ? texture.getDao().getTexture() :
                generateTexture(resolution, GL_DEPTH_COMPONENT24, GL_DEPTH_COMPONENT, GL_FLOAT);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, textureId, 0);

        return textureId;
    }

    private int createAttachment(final FramebufferAttachment attachment, final Vector2i resolution, final Texture texture) {
        switch (attachment) {
            case COLOR_TEXTURE_ATTACHMENT:
                return createColorTextureAttachment(resolution, texture);
            case DEPTH_TEXTURE_ATTACHMENT:
                return createDepthTextureAttachment(resolution, texture);
            case DEPTH_BUFFER_ATTACHMENT:
                return createDepthBufferAttachment(resolution);
            case DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT:
                return createDepthBufferMultisampleAttachment(resolution);
            case COLOR_BUFFER_MULTISAMPLE_ATTACHMENT:
                return createColorBufferMultisampleAttachment(resolution);
            default:
                throw new PIEngineException("Invalid framebuffer attachment: %s", attachment.name());
        }
    }

    private int createDepthBufferAttachment(final Vector2i resolution) {
        int rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, resolution.x, resolution.y);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);

        return rbo;
    }

    private int createDepthBufferMultisampleAttachment(final Vector2i resolution) {
        int rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, SAMPLES, GL_DEPTH_COMPONENT, resolution.x, resolution.y);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rbo);

        return rbo;
    }

    private int createColorBufferMultisampleAttachment(final Vector2i resolution) {
        int rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, SAMPLES, GL_RGBA8, resolution.x, resolution.y);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, rbo);

        return rbo;
    }

    private int generateTexture(final Vector2i resolution, final int internalFormat, final int format, final int type) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, resolution.x, resolution.y, 0, format, type, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        return texture;
    }
}
