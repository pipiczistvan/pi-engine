package piengine.gui.asset;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import piengine.common.gui.writing.font.domain.Font;
import piengine.common.gui.writing.text.domain.Text;
import piengine.core.base.event.Event;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.TexturedModel;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.writing.font.manager.FontManager;
import piengine.visual.writing.text.manager.TextManager;
import puppeteer.annotation.premade.Wire;

import static piengine.visual.render.domain.AssetPlan.createPlan;
import static piengine.visual.render.domain.RenderType.RENDER_PLANE_MODEL;
import static piengine.visual.render.domain.RenderType.RENDER_TEXT;
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
    private TexturedModel buttonModel;
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
        label = textManager.supply(textConfig().withFont(font).withText("press me"));

        buttonModel = modelManager.supply("square", this, defaultImage);
        buttonModel.setScale(SCALE_X, SCALE_Y, 1.0f);

        setupButtonParameters();

        inputManager.addEvent(v -> {
            hover = v.x >= x && v.x <= x + width && v.y >= y && v.y <= y + height;

            if (pressed) {
                buttonModel.texture = pressImage;
            } else {
                if (hover) {
                    buttonModel.texture = hoverImage;
                } else {
                    buttonModel.texture = defaultImage;
                }
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.PRESS, () -> {
            pressed = hover;
            if (pressed) {
                buttonModel.texture = pressImage;
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.RELEASE, () -> {
            if (pressed && hover) {
                arguments.onClickEvent.invoke();
            }
            pressed = false;
            buttonModel.texture = defaultImage;
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
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withPlan(preparePlan())
                .withModel(buttonModel)
                .doRender(RENDER_PLANE_MODEL)

                .withPlan(preparePlan())
                .withText(label)
                .doRender(RENDER_TEXT);
    }

    private void setupButtonParameters() {
        Vector3f scale = buttonModel.getScale();
        Vector3f position = buttonModel.getPosition();

        width = arguments.viewport.x * scale.x;
        height = arguments.viewport.y * scale.y;
        x = (arguments.viewport.x - width + arguments.viewport.x * position.x) / 2;
        y = (arguments.viewport.y - height + arguments.viewport.y * position.y) / 2;
    }

    public static ButtonAssetArgument createArguments(final String defaultImageName,
                                                      final String hoverImageName,
                                                      final String pressImageName,
                                                      final Vector2i viewport,
                                                      final Event onClickEvent) {
        return new ButtonAssetArgument(defaultImageName, hoverImageName, pressImageName, viewport, onClickEvent);
    }


    private AssetPlan preparePlan() {
        return createPlan()
                .withClearColor(new Vector4f(1))
                .withViewport(new Vector2i(800, 600));
    }
}
