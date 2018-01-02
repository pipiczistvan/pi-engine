package piengine.gui.asset;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.writing.font.domain.Font;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.domain.Text;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.RenderPlan.createPlan;
import static piengine.visual.writing.text.domain.TextConfiguration.textConfig;

public class ButtonAsset extends Asset<ButtonAssetArgument> {

    private static final float SCALE_X = 0.25f;
    private static final float SCALE_Y = 0.125f;

    private final ImageManager imageManager;
    private final ModelManager modelManager;
    private final InputManager inputManager;
    private final FontManager fontManager;
    private final TextManager textManager;

    private Image defaultImage;
    private Image hoverImage;
    private Image pressImage;
    private Model buttonModel;
    private Font font;
    private Text label;

    private float x, y, width, height;
    private boolean hover = false;
    private boolean pressed = false;

    @Wire
    public ButtonAsset(final RenderManager renderManager, final ImageManager imageManager,
                       final ModelManager modelManager, final InputManager inputManager,
                       final FontManager fontManager, final TextManager textManager) {
        super(renderManager);

        this.imageManager = imageManager;
        this.modelManager = modelManager;
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

        buttonModel = modelManager.supply("square", this, defaultImage);
        buttonModel.setScale(SCALE_X, SCALE_Y, 1.0f);

        setupButtonParameters();

        inputManager.addEvent(v -> {
            hover = v.x >= x && v.x <= x + width && v.y >= y && v.y <= y + height;

            if (pressed) {
                buttonModel.attribute.texture = pressImage;
            } else {
                if (hover) {
                    buttonModel.attribute.texture = hoverImage;
                } else {
                    buttonModel.attribute.texture = defaultImage;
                }
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.PRESS, () -> {
            pressed = hover;
            if (pressed) {
                buttonModel.attribute.texture = pressImage;
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.RELEASE, () -> {
            if (pressed && hover) {
                arguments.onClickEvent.invoke();
            }
            pressed = false;
            buttonModel.attribute.texture = defaultImage;
        });
    }

    @Override
    public void update(double delta) {

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

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan()
                .renderToGui(arguments.viewport, buttonModel)
                .renderText(arguments.viewport, label);
    }

    private void setupButtonParameters() {
        Vector3f scale = buttonModel.getScale();
        Vector3f position = buttonModel.getPosition();

        width = arguments.viewport.x * scale.x;
        height = arguments.viewport.y * scale.y;
        x = (arguments.viewport.x - width + arguments.viewport.x * position.x) / 2;
        y = (arguments.viewport.y - height + arguments.viewport.y * position.y) / 2;
    }
}
