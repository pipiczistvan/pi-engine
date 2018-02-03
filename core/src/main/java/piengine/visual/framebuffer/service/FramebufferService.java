package piengine.visual.framebuffer.service;

import piengine.core.base.resource.SupplierService;
import piengine.visual.framebuffer.accessor.FramebufferAccessor;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferDao;
import piengine.visual.framebuffer.domain.FramebufferData;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.interpreter.FramebufferInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.Stack;

@Component
public class FramebufferService extends SupplierService<FramebufferKey, FramebufferData, FramebufferDao, Framebuffer> {

    private final FramebufferInterpreter framebufferInterpreter;
    private final Stack<Framebuffer> fboStack;

    @Wire
    public FramebufferService(final FramebufferAccessor framebufferAccessor, final FramebufferInterpreter framebufferInterpreter) {
        super(framebufferAccessor, framebufferInterpreter);

        this.framebufferInterpreter = framebufferInterpreter;
        this.fboStack = new Stack<>();
    }

    @Override
    protected Framebuffer createDomain(final FramebufferDao dao, final FramebufferData resource) {
        return new Framebuffer(dao, resource.key, resource.resolution);
    }

    public void bind(final Framebuffer framebuffer) {
        framebufferInterpreter.bind(framebuffer.getDao());
        fboStack.push(framebuffer);
    }

    public void unbind() {
        fboStack.pop();
        rebindLast();
    }

    public void blit(final Framebuffer src, final Framebuffer dest) {
        framebufferInterpreter.blit(src, dest);
        rebindLast();
    }

    public void cleanUp(final Framebuffer framebuffer) {
        framebufferInterpreter.free(framebuffer.getDao());
        removeForKey(framebuffer.key);
    }

    private void rebindLast() {
        if (fboStack.empty()) {
            framebufferInterpreter.unbind();
        } else {
            Framebuffer framebuffer = fboStack.peek();
            framebufferInterpreter.bind(framebuffer.getDao());
        }
    }
}
