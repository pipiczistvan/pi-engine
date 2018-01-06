package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.TextureDao;

import java.util.Map;

import static piengine.visual.framebuffer.domain.FrameBufferAttachment.COLOR_ATTACHMENT;

public class FrameBufferDao extends TextureDao {

    public final int fbo;
    public final Map<FrameBufferAttachment, Integer> attachments;

    public FrameBufferDao(final int fbo, final Map<FrameBufferAttachment, Integer> attachments) {
        this.fbo = fbo;
        this.attachments = attachments;
    }

    @Override
    public int getTexture() {
        return attachments.getOrDefault(COLOR_ATTACHMENT, -1);
    }
}
