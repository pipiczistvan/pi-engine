package piengine.gui.asset;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.manager.CanvasManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.writing.text.domain.TextConfiguration.textConfig;

public class ButtonAsset extends Asset<ButtonAssetArgument, GuiRenderAssetContext> {

    private static final float SCALE_X = 0.25f;
    private static final float SCALE_Y = 0.125f;

    private final ImageManager imageManager;
    private final CanvasManager canvasManager;
    private final InputManager inputManager;
    private final FontManager fontManager;
    private final TextManager textManager;

    private Image defaultImage;
    private Image hoverImage;
    private Image pressImage;
    private Canvas buttonCanvas;
    private Font font;
    private Text label;

    private float x, y, width, height;
    private boolean hover = false;
    private boolean pressed = false;

    @Wire
    public ButtonAsset(final ImageManager imageManager, final CanvasManager canvasManager,
                       final InputManager inputManager, final FontManager fontManager,
                       final TextManager textManager) {
        this.imageManager = imageManager;
        this.canvasManager = canvasManager;
        this.inputManager = inputManager;
        this.fontManager = fontManager;
        this.textManager = textManager;
    }

    @Override
    public void initialize() {
        defaultImage = imageManager.supply(arguments.defaultImageName);
        hoverImage = imageManager.supply(arguments.hoverImageName);
        pressImage = imageManager.supply(arguments.pressImageName);

        font = fontManager.supply("candara");
        label = textManager.supply(textConfig().withFont(font).withFontSize(1.5f).withMaxLineLength(SCALE_X).withText(arguments.text), this);

        buttonCanvas = canvasManager.supply(this, defaultImage);
        buttonCanvas.setScale(SCALE_X, SCALE_Y, 1.0f);

        setupButtonParameters();

        inputManager.addEvent(v -> {
            hover = v.x >= x && v.x <= x + width && v.y >= y && v.y <= y + height;

            if (pressed) {
                buttonCanvas.texture = pressImage;
            } else {
                if (hover) {
                    buttonCanvas.texture = hoverImage;
                } else {
                    buttonCanvas.texture = defaultImage;
                }
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.PRESS, () -> {
            pressed = hover;
            if (pressed) {
                buttonCanvas.texture = pressImage;
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.RELEASE, () -> {
            if (pressed && hover) {
                arguments.onClickEvent.invoke();
            }
            pressed = false;
            if (hover) {
                buttonCanvas.texture = hoverImage;
            } else {
                buttonCanvas.texture = defaultImage;
            }
        });
    }

    @Override
    public void update(final float delta) {

    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadCanvases(buttonCanvas)
                .loadTexts(label)
                .build();
    }

    @Override
    public void setPosition(float x, float y, float z) {
        super.setPosition(x, y, z);
        setupButtonParameters();
    }

    @Override
    public void setScale(float x, float y, float z) {
        super.setScale(x, y, z);
        setupButtonParameters();
    }

    private void setupButtonParameters() {
        Vector3f scale = buttonCanvas.getScale();
        Vector3f position = buttonCanvas.getPosition();

        width = arguments.viewport.x * scale.x;
        height = arguments.viewport.y * scale.y;
        x = (arguments.viewport.x - width + arguments.viewport.x * position.x) / 2;
        y = (arguments.viewport.y - height - arguments.viewport.y * position.y) / 2;
    }
}
