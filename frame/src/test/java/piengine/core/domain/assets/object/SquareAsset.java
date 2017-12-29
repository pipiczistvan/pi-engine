package piengine.core.domain.assets.object;

import org.joml.Vector2i;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.framebuffer.domain.FrameBuffer;
import piengine.visual.framebuffer.domain.FrameBufferData;
import piengine.visual.framebuffer.manager.FrameBufferManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class SquareAsset extends Asset {

    private final ModelManager modelManager;
    private final FrameBufferManager frameBufferManager;
    private final ImageManager imageManager;

    private Model square;
    private FrameBuffer frameBuffer;
    private Image image;

    @Wire
    public SquareAsset(final RenderManager renderManager, final ModelManager modelManager,
                       final FrameBufferManager frameBufferManager, final ImageManager imageManager) {
        super(renderManager);

        this.modelManager = modelManager;
        this.frameBufferManager = frameBufferManager;
        this.imageManager = imageManager;
    }

    //todo: pass arguments when creating

    @Override
    public void initialize() {
        square = modelManager.supply("square", this);
//        square.setScale(0.5f);
//        square.setPosition(-0.5f, -0.5f, 0.0f);

        frameBuffer = frameBufferManager.supply(new FrameBufferData(new Vector2i(800, 600)));

        image = imageManager.supply("coord");
    }

    public FrameBuffer getFrameBuffer() {
        return frameBuffer;
    }

    @Override
    public void update(double delta) {

    }

    //todo: sceneplan nesting
    //todo: texture coord hacking fix

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withTexture(frameBuffer)
                .withModel(square);
    }

}
