package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.TextureDao;

import java.util.Map;

public class FramebufferDao extends TextureDao {

    private final int fbo;
    private final Map<FramebufferAttachment, Integer> attachments;
    private final FramebufferAttachment textureAttachment;

    public FramebufferDao(final int fbo, final Map<FramebufferAttachment, Integer> attachments, final FramebufferAttachment textureAttachment) {
        this.fbo = fbo;
        this.attachments = attachments;
        this.textureAttachment = textureAttachment;
    }

    public int getFbo() {
        return fbo;
    }

    public Map<FramebufferAttachment, Integer> getAttachments() {
        return attachments;
    }

    public int getAttachment(final FramebufferAttachment attachment) {
        return attachments.get(attachment);
    }

    @Override
    public int getTexture() {
        return attachments.get(textureAttachment);
    }
}
