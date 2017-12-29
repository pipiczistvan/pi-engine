package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.TextureDao;

public class FrameBufferDao extends TextureDao {

    public final int fbo;
    public final int rbo;

    public FrameBufferDao(final int fbo, final int texture, final int rbo) {
        super(texture);
        this.fbo = fbo;
        this.rbo = rbo;
    }
}
