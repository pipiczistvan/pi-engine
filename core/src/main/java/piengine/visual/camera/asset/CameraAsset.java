package piengine.visual.camera.asset;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.visual.camera.domain.Camera;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;
import static piengine.visual.render.domain.RenderPlan.createPlan;

public abstract class CameraAsset<C extends Camera> extends Asset<CameraAssetArgument> {

    public final C camera;
    public boolean movingEnabled = true;
    public boolean lookingEnabled = true;

    private static final float GRAVITY = -100;
    private static final float JUMP_POWER = 30;
    private static final float TERRAIN_HEIGHT = 0;

    private final Vector2f movement;
    private float upwardsSpeed = 0;
    private boolean isInAir = false;

    private final InputManager inputManager;
    private final WindowManager windowManager;

    public CameraAsset(final RenderManager renderManager, final InputManager inputManager, final WindowManager windowManager) {
        super(renderManager);

        this.camera = getCamera();
        this.inputManager = inputManager;
        this.windowManager = windowManager;

        this.movement = new Vector2f();
    }

    @Override
    public void initialize() {
        inputManager.addEvent(GLFW_KEY_SPACE, RELEASE, this::jump);
        inputManager.addEvent(GLFW_KEY_A, PRESS, this::moveLeft);
        inputManager.addEvent(GLFW_KEY_S, PRESS, this::moveBackward);
        inputManager.addEvent(GLFW_KEY_D, PRESS, this::moveRight);
        inputManager.addEvent(GLFW_KEY_W, PRESS, this::moveForward);
        inputManager.addEvent(GLFW_KEY_A, RELEASE, this::moveRight);
        inputManager.addEvent(GLFW_KEY_S, RELEASE, this::moveForward);
        inputManager.addEvent(GLFW_KEY_D, RELEASE, this::moveLeft);
        inputManager.addEvent(GLFW_KEY_W, RELEASE, this::moveBackward);
        inputManager.addEvent(v -> {
            if (lookingEnabled) {
                Vector2f delta = new Vector2f();
                Vector2f windowCenter = windowManager.getWindowCenter();
                v.sub(windowCenter, delta);
                if (Math.abs(delta.x) >= 1 || Math.abs(delta.y) >= 1) {
                    lookAt(delta);
                    windowManager.setPointer(windowCenter);
                }
            }
        });
    }

    @Override
    public void update(double delta) {
        // HORIZONTAL MOVEMENT
        float translateX = 0;
        float translateZ = 0;
        Vector3f rotation = getRotation();

        float multiplier;
        if (movement.x != 0 && movement.y != 0) {
            multiplier = arguments.strafeSpeed;
        } else {
            multiplier = arguments.moveSpeed;
        }

        if (movement.x < 0) {
            translateX += Math.sin(Math.toRadians(rotation.x - 90));
            translateZ -= Math.cos(Math.toRadians(rotation.x - 90));
        } else if (movement.x > 0) {
            translateX -= Math.sin(Math.toRadians(rotation.x - 90));
            translateZ += Math.cos(Math.toRadians(rotation.x - 90));
        }

        if (movement.y < 0) {
            translateX += Math.sin(Math.toRadians(rotation.x));
            translateZ -= Math.cos(Math.toRadians(rotation.x));
        } else if (movement.y > 0) {
            translateX -= Math.sin(Math.toRadians(rotation.x));
            translateZ += Math.cos(Math.toRadians(rotation.x));
        }

        addPosition(translateX * (float) delta * multiplier, 0, translateZ * (float) delta * multiplier);

        // VERTICAL MOVEMENT
        upwardsSpeed += GRAVITY * delta;
        addPosition(0, upwardsSpeed * (float) delta, 0);

        Vector3f position = getPosition();
        float terrainHeight = arguments.terrain != null ?
                arguments.terrain.getHeight(position.x, position.z) : 0;

        if (position.y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            setPosition(position.x, terrainHeight, position.z);
        }

        // VIEW MATRIX CALCULATION
        camera.update(delta);
    }

    protected abstract C getCamera();

    @Override
    protected RenderPlan createRenderPlan() {
        return createPlan();
    }

    private void jump() {
        if (!isInAir) {
            upwardsSpeed = JUMP_POWER;
        }
        isInAir = true;
    }

    private void moveLeft() {
        if (movingEnabled) {
            moveHorizontal(-1);
        }
    }

    private void moveRight() {
        if (movingEnabled) {
            moveHorizontal(1);
        }
    }

    private void moveForward() {
        if (movingEnabled) {
            moveVertical(-1);
        }
    }

    private void moveBackward() {
        if (movingEnabled) {
            moveVertical(1);
        }
    }

    private void moveHorizontal(final float direction) {
        movement.x += movement.x == direction ? 0 : direction;
    }

    private void moveVertical(final float direction) {
        movement.y += movement.y == direction ? 0 : direction;
    }

    private void lookAt(final Vector2f lookAt) {
        addRotation(lookAt.x * arguments.lookSpeed, -lookAt.y * arguments.lookSpeed, 0.0f);

        Vector3f rotation = getRotation();

        clampYaw(rotation.x);
        clampPitch(rotation);
    }

    private void clampYaw(final float yaw) {
        if (yaw > 360) {
            addRotation(-360, 0, 0);
        } else if (yaw < 0) {
            addRotation(360, 0, 0);
        }
    }

    private void clampPitch(final Vector3f rotation) {
        if (rotation.y > arguments.lookUpLimit) {
            setRotation(rotation.x, arguments.lookUpLimit, rotation.z);
        } else if (rotation.y < arguments.lookDownLimit) {
            setRotation(rotation.x, arguments.lookDownLimit, rotation.z);
        }
    }
}
