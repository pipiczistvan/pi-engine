package piengine.core.domain.assets.object;

import piengine.common.gui.writing.font.domain.Font;
import piengine.common.gui.writing.text.domain.Text;
import piengine.core.time.manager.TimeManager;
import piengine.object.asset.domain.Asset;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;
import static piengine.visual.writing.text.domain.TextConfiguration.textConfig;

public class TextAsset extends Asset {

    private final FontManager fontManager;
    private final TextManager textManager;
    private final TimeManager timeManager;

    private Text myText;
    private TextConfiguration textConfig;

    @Wire
    public TextAsset(final RenderManager renderManager,
                     final FontManager fontManager,
                     final TextManager textManager,
                     final TimeManager timeManager) {
        super(renderManager);

        this.fontManager = fontManager;
        this.textManager = textManager;
        this.timeManager = timeManager;
    }

    @Override
    public void initialize() {
        final Font candara = fontManager.supply("candara");

        textConfig = textConfig()
                .withFontSize(3)
                .withFont(candara)
                .withMaxLineLength(1);
        myText = textManager.supply(textConfig);
    }

    @Override
    public void update(double delta) {
        textManager.update(myText, textConfig
                .withText(String.format("FPS: %d", timeManager.getFPS()))
        );
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withText(myText);
    }

}
