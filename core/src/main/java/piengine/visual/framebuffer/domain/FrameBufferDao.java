package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.TextureDao;

import java.util.Map;

public class FrameBufferDao extends TextureDao {

    public final int fbo;
    public final Map<FrameBufferAttachment, Integer> attachments;
    public final FrameBufferAttachment textureAttachment;

    public FrameBufferDao(final int fbo, final Map<FrameBufferAttachment, Integer> attachments, final FrameBufferAttachment textureAttachment) {
        this.fbo = fbo;
        this.attachments = attachments;
        this.textureAttachment = textureAttachment;
    }

    @Override
    public int getTexture() {
        return attachments.get(textureAttachment);
    }
}
