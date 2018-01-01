package piengine.object.model.asset;

import org.joml.Vector4f;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.model.domain.Model;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.texture.domain.Texture;

import java.util.List;

public abstract class ModelAsset<T extends AssetArgument> extends Asset<T> {

    public final List<Model> models;
    public final Texture texture;
    public final Vector4f color;

    public ModelAsset(final RenderManager renderManager) {
        super(renderManager);

        models = getModels();
        texture = getTexture();
        color = getColor();
    }

    protected abstract List<Model> getModels();

    protected abstract Texture getTexture();

    protected abstract Vector4f getColor();
}
