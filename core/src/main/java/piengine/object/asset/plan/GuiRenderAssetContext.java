package piengine.object.asset.plan;

import piengine.object.canvas.domain.Canvas;
import piengine.visual.writing.text.domain.Text;

import java.util.ArrayList;
import java.util.List;

public class GuiRenderAssetContext implements RenderAssetContext {

    public final List<Canvas> canvases;
    public final List<Text> texts;

    GuiRenderAssetContext() {
        this.canvases = new ArrayList<>();
        this.texts = new ArrayList<>();
    }
}
