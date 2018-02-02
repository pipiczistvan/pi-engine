package piengine.object.water.service;

import org.joml.Vector2i;
import piengine.core.base.resource.SupplierService;
import piengine.object.water.accessor.WaterAccessor;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterDao;
import piengine.object.water.domain.WaterData;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.interpreter.WaterInterpreter;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;

@Component
public class WaterService extends SupplierService<WaterKey, WaterData, WaterDao, Water> {

    private final FramebufferManager framebufferManager;

    @Wire
    public WaterService(final WaterAccessor waterAccessor, final WaterInterpreter waterInterpreter,
                        final FramebufferManager framebufferManager) {
        super(waterAccessor, waterInterpreter);

        this.framebufferManager = framebufferManager;
    }

    @Override
    protected Water createDomain(final WaterDao dao, final WaterData resource) {
        Vector2i reflectionResolution = new Vector2i(
                resource.resolution.x,
                resource.resolution.y
        );
        Vector2i refractionResolution = new Vector2i(
                resource.resolution.x / 2,
                resource.resolution.y / 2
        );

        Framebuffer reflectionBuffer = framebufferManager.supply(reflectionResolution, COLOR_TEXTURE_ATTACHMENT, DEPTH_BUFFER_ATTACHMENT);
        Framebuffer refractionBuffer = framebufferManager.supply(refractionResolution, COLOR_TEXTURE_ATTACHMENT, DEPTH_TEXTURE_ATTACHMENT);

        return new Water(dao, reflectionBuffer, refractionBuffer, resource.position, resource.rotation, resource.scale, resource.color);
    }

    public void resize(final Water water, final Vector2i resolution) {
        framebufferManager.resize(water.reflectionBuffer, resolution);
        framebufferManager.resize(water.refractionBuffer, new Vector2i(resolution.x / 2, resolution.y / 2));
    }
}
