package piengine.object.asset.domain;

import piengine.object.canvas.domain.Canvas;
import piengine.visual.render.domain.plan.GuiRenderPlanBuilder;
import piengine.visual.writing.text.domain.Text;

public abstract class GuiAsset<T extends AssetArgument> extends Asset<T, GuiRenderPlanBuilder> {

    public Canvas[] getCanvases() {
        return new Canvas[0];
    }

    public Text[] getTexts() {
        return new Text[0];
    }
}
