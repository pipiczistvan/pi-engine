package piengine.visual.framebuffer.domain;

import piengine.core.base.domain.Domain;

public class FrameBuffer extends Domain<FrameBufferDao> {

    public FrameBuffer(final FrameBufferDao dao) {
        super(dao);
    }
}
