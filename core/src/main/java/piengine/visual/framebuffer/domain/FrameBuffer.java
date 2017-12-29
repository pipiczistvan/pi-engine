package piengine.visual.framebuffer.domain;

import piengine.visual.texture.domain.Texture;

public class FrameBuffer extends Texture<FrameBufferDao> {

    public FrameBuffer(final FrameBufferDao dao) {
        super(dao);
    }
}
