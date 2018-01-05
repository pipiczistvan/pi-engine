package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.TextureDao;

public class FrameBufferDao extends TextureDao {

    public final int fbo;
    public final int rbo;
    public final int[] textures;

    public FrameBufferDao(final int fbo, final int rbo, final int[] textures) {
        this.fbo = fbo;
        this.rbo = rbo;
        this.textures = textures;
    }

    @Override
    public int getTexture() {
        return textures[0]; //todo: temporary solution (index)
    }
}
