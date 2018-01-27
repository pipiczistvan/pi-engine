package piengine.object.asset.plan;

import piengine.object.asset.domain.GuiAsset;
import piengine.object.canvas.domain.Canvas;
import piengine.visual.writing.text.domain.Text;

import java.util.Arrays;

public class GuiRenderAssetContextBuilder {

    private final GuiRenderAssetContext assetContext;

    private GuiRenderAssetContextBuilder() {
        assetContext = new GuiRenderAssetContext();
    }

    public static GuiRenderAssetContextBuilder create() {
        return new GuiRenderAssetContextBuilder();
    }

    public GuiRenderAssetContextBuilder loadCanvases(final Canvas... canvases) {
        this.assetContext.canvases.addAll(Arrays.asList(canvases));
        return this;
    }

    public GuiRenderAssetContextBuilder loadTexts(final Text... texts) {
        this.assetContext.texts.addAll(Arrays.asList(texts));
        return this;
    }

    public GuiRenderAssetContextBuilder loadAssets(final GuiAsset... assets) {
        for (GuiAsset asset : assets) {
            loadAssetContexts((GuiRenderAssetContext) asset.getAssetContext());
        }
        return this;
    }

    public GuiRenderAssetContextBuilder loadAssetContexts(final GuiRenderAssetContext... assetContexts) {
        for (GuiRenderAssetContext assetContext : assetContexts) {
            this.assetContext.canvases.addAll(assetContext.canvases);
            this.assetContext.texts.addAll(assetContext.texts);
        }
        return this;
    }

    public GuiRenderAssetContext build() {
        return assetContext;
    }
}
