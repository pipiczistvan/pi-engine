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
import piengine.visual.framebuffer.domain.FramebufferKey;
import piengine.visual.framebuffer.service.FramebufferService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.RENDER_BUFFER_ATTACHMENT;

@Component
public class WaterService extends SupplierService<WaterKey, WaterData, WaterDao, Water> {

    private final FramebufferService framebufferService;

    @Wire
    public WaterService(final WaterAccessor waterAccessor, final WaterInterpreter waterInterpreter,
                        final FramebufferService framebufferService) {
        super(waterAccessor, waterInterpreter);

        this.framebufferService = framebufferService;
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

        Framebuffer reflectionBuffer = framebufferService.supply(new FramebufferKey(reflectionResolution,
                COLOR_ATTACHMENT, RENDER_BUFFER_ATTACHMENT
        ));
        Framebuffer refractionBuffer = framebufferService.supply(new FramebufferKey(refractionResolution,
                COLOR_ATTACHMENT, DEPTH_TEXTURE_ATTACHMENT
        ));

        return new Water(null, dao, reflectionBuffer, refractionBuffer);
    }
}
