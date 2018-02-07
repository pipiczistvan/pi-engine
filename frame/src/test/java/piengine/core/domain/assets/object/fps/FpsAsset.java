package piengine.core.domain.assets.object.fps;

import org.joml.Vector2f;
import piengine.core.time.manager.TimeManager;
import piengine.core.utils.ColorUtils;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.domain.TextConfiguration;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

public class FpsAsset extends GuiAsset<FpsAssetArgument> {

    private final FontManager fontManager;
    private final TextManager textManager;
    private final TimeManager timeManager;

    private Font font;
    private Text fpsText;
    private TextConfiguration config;

    @Wire
    public FpsAsset(final AssetManager assetManager, final FontManager fontManager,
                    final TextManager textManager, final TimeManager timeManager) {
        super(assetManager);

        this.fontManager = fontManager;
        this.textManager = textManager;
        this.timeManager = timeManager;
    }

    @Override
    public void initialize() {
        font = fontManager.supply("candara", arguments.viewport);
        config = TextConfiguration.textConfig().withFont(font).withFontSize(2)
                .withColor(ColorUtils.WHITE).withOutlineColor(ColorUtils.BLACK)
                .withOffset(new Vector2f(-0.004f));
        fpsText = textManager.supply(config, this);
        fpsText.setPosition(0.85f, 0.85f, 0);
    }

    @Override
    public void update(final float delta) {
        textManager.update(fpsText, config.withText("FPS: " + timeManager.getFPS()));
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadTexts(fpsText)
                .build();
    }
}
