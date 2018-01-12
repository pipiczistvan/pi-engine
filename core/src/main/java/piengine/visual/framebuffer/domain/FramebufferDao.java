package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.TextureDao;

import java.util.Map;

public class FramebufferDao extends TextureDao {

    public final int fbo;
    public final Map<FramebufferAttachment, Integer> attachments;
    public final FramebufferAttachment textureAttachment;

    public FramebufferDao(final int fbo, final Map<FramebufferAttachment, Integer> attachments, final FramebufferAttachment textureAttachment) {
        this.fbo = fbo;
        this.attachments = attachments;
        this.textureAttachment = textureAttachment;
    }

    @Override
    public int getTexture() {
        return attachments.get(textureAttachment);
    }
}
