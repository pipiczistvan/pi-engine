package piengine.object.water.service;

import org.joml.Vector2i;
import piengine.core.base.resource.SupplierService;
import piengine.object.water.accessor.WaterAccessor;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterDao;
import piengine.object.water.domain.WaterData;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.interpreter.WaterInterpreter;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.service.FrameBufferService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.framebuffer.domain.FrameBufferAttachment.COLOR_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.DEPTH_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FrameBufferAttachment.RENDER_BUFFER_ATTACHMENT;

@Component
public class WaterService extends SupplierService<WaterKey, WaterData, WaterDao, Water> {

    private final FrameBufferService frameBufferService;

    @Wire
    public WaterService(final WaterAccessor waterAccessor, final WaterInterpreter waterInterpreter,
                        final FrameBufferService frameBufferService) {
        super(waterAccessor, waterInterpreter);

        this.frameBufferService = frameBufferService;
    }

    @Override
    protected Water createDomain(final WaterDao dao, final WaterData resource) {
        //todo: temporary solution (viewport)
        FrameBuffer reflectionBuffer = frameBufferService.supply(new FrameBufferData(
                new Vector2i(800, 600),
                COLOR_ATTACHMENT, RENDER_BUFFER_ATTACHMENT
        ));
        FrameBuffer refractionBuffer = frameBufferService.supply(new FrameBufferData(
                new Vector2i(800, 600),
                COLOR_ATTACHMENT, DEPTH_TEXTURE_ATTACHMENT
        ));

        return new Water(resource.parent, dao, reflectionBuffer, refractionBuffer);
    }
}
