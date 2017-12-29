package piengine.visual.framebuffer.service;

import piengine.core.base.api.Terminatable;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferDao;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.interpreter.FrameBufferInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class FrameBufferService implements Terminatable {

    private final List<FrameBuffer> frameBuffers;
    private final FrameBufferInterpreter frameBufferInterpreter;

    @Wire
    public FrameBufferService(final FrameBufferInterpreter frameBufferInterpreter) {
        this.frameBufferInterpreter = frameBufferInterpreter;
        this.frameBuffers = new ArrayList<>();
    }

    public FrameBuffer supply(final FrameBufferData frameBufferData) {
        FrameBufferDao dao = frameBufferInterpreter.create(frameBufferData);
        FrameBuffer frameBuffer = new FrameBuffer(dao);

        frameBuffers.add(frameBuffer);

        return frameBuffer;
    }

    @Override
    public void terminate() {
        for (FrameBuffer frameBuffer : frameBuffers) {
            frameBufferInterpreter.free(frameBuffer.dao);
        }
    }

    public void bind(final FrameBuffer frameBuffer) {
        frameBufferInterpreter.bind(frameBuffer.dao);
    }

    public void unbind() {
        frameBufferInterpreter.unbind();
    }
}
