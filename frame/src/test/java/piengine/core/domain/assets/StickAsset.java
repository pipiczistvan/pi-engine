package piengine.core.domain.assets;

import org.joml.Vector4f;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class StickAsset extends Asset {

    private final ModelManager modelManager;

    private final Random random = new Random();
    private final List<Model> sticks = new ArrayList<>();

    @Wire
    public StickAsset(RenderManager renderManager, ModelManager modelManager) {
        super(renderManager);
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < 100; i++) {
            Model stick = modelManager.supply("octahedron", this);

            stick.setPosition(
                    random.nextFloat() * 4 - 2f,
                    random.nextFloat() * 4 - 2f,
                    random.nextFloat() * 4 - 2f);
            stick.setRotation(
                    random.nextFloat() * 360 - 180,
                    random.nextFloat() * 360 - 180,
                    random.nextFloat() * 360 - 180);
            stick.setScale(0.0025f, 0.2f, 0.0025f);

            sticks.add(stick);
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModels(sticks)
                .withColor(new Vector4f(0, 0, 0, 1));
    }

}
