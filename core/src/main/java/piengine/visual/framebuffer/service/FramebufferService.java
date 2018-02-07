package piengine.visual.framebuffer.service;

import org.joml.Vector2i;
import piengine.core.base.api.Resizable;
import piengine.core.base.resource.SupplierService;
import piengine.visual.framebuffer.accessor.FramebufferAccessor;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.domain.FramebufferAttachment;
import piengine.visual.framebuffer.domain.FramebufferDao;
import piengine.visual.framebuffer.domain.FramebufferData;
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.interpreter.FramebufferInterpreter;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.Map;
import java.util.Stack;

@Component
public class FramebufferService extends SupplierService<FramebufferKey, FramebufferData, FramebufferDao, Framebuffer> implements Resizable {

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
        return new Framebuffer(dao, new Vector2i(resource.resolution), resource.fixed);
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

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        float scaleX = (float) width / (float) oldWidth;
        float scaleY = (float) height / (float) oldHeight;

        for (Map.Entry<FramebufferKey, Framebuffer> entry : domainMap.entrySet()) {
            FramebufferKey oldKey = entry.getKey();
            Framebuffer oldFramebuffer = entry.getValue();

            if (!oldFramebuffer.isFixed()) {
                framebufferInterpreter.free(oldFramebuffer.getDao());

                float newWidth = oldFramebuffer.getSize().x * scaleX;
                float newHeight = oldFramebuffer.getSize().y * scaleY;
                Framebuffer newFramebuffer = copyFramebufferWithNewSize(oldKey, new Vector2i((int) newWidth, (int) newHeight));

                oldFramebuffer.setDao(newFramebuffer.getDao());
                oldFramebuffer.setSize(newFramebuffer.getSize());
            }
        }
    }

    private Framebuffer copyFramebufferWithNewSize(final FramebufferKey oldKey, final Vector2i newSize) {
        FramebufferAttachment[] otherAttachments = new FramebufferAttachment[oldKey.attachments.length - 1];
        System.arraycopy(oldKey.attachments, 1, otherAttachments, 0, otherAttachments.length);

        return computeDomain(new FramebufferKey(
                newSize,
                oldKey.texture,
                oldKey.drawingEnabled,
                oldKey.fixed,
                oldKey.attachments[0],
                otherAttachments
        ));
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
