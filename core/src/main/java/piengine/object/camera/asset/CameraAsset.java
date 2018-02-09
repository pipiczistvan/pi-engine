package piengine.object.camera.asset;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.RenderAssetContext;
import piengine.visual.display.manager.DisplayManager;
import puppeteer.annotation.premade.Wire;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;

public class CameraAsset extends Asset<CameraAssetArgument, RenderAssetContext> {

    private static final float GRAVITY = -100;
    private static final float JUMP_POWER = 30;
    private static final float CAMERA_HEIGHT = 3;
    private final Vector2f movement;
    private final Vector2f looking;
    private final InputManager inputManager;
    private final DisplayManager displayManager;
    public boolean movingEnabled = true;
    public boolean lookingEnabled = true;
    public boolean flyingEnabled = true;
    private float upwardsSpeed = 0;
    private boolean isInAir = false;
    private boolean isFlying = false;

    @Wire
    public CameraAsset(final AssetManager assetManager, final InputManager inputManager, final DisplayManager displayManager) {
        super(assetManager);

        this.inputManager = inputManager;
        this.displayManager = displayManager;

        this.movement = new Vector2f();
        this.looking = new Vector2f();
    }

    @Override
    public void initialize() {
        inputManager.addKeyEvent(GLFW_KEY_SPACE, PRESS, this::pressSpace);
        inputManager.addKeyEvent(GLFW_KEY_SPACE, RELEASE, this::releaseSpace);
        inputManager.addKeyEvent(GLFW_KEY_LEFT_SHIFT, PRESS, this::pressShift);
        inputManager.addKeyEvent(GLFW_KEY_LEFT_SHIFT, RELEASE, this::releaseShift);
        inputManager.addKeyEvent(GLFW_KEY_A, PRESS, this::moveLeft);
        inputManager.addKeyEvent(GLFW_KEY_S, PRESS, this::moveBackward);
        inputManager.addKeyEvent(GLFW_KEY_D, PRESS, this::moveRight);
        inputManager.addKeyEvent(GLFW_KEY_W, PRESS, this::moveForward);
        inputManager.addKeyEvent(GLFW_KEY_A, RELEASE, this::moveRight);
        inputManager.addKeyEvent(GLFW_KEY_S, RELEASE, this::moveForward);
        inputManager.addKeyEvent(GLFW_KEY_D, RELEASE, this::moveLeft);
        inputManager.addKeyEvent(GLFW_KEY_W, RELEASE, this::moveBackward);
        inputManager.addCursorEvent(v -> {
            if (lookingEnabled) {
                Vector2f delta = new Vector2f();
                Vector2f windowCenter = displayManager.getDisplayCenter();
                v.sub(windowCenter, delta);
                if (Math.abs(delta.x) >= 1 || Math.abs(delta.y) >= 1) {
                    looking.set(delta);
                    displayManager.setPointer(windowCenter);
                }
            }
        });
    }

    @Override
    public void update(final float delta) {
        Vector3f newPosition = calculatePosition(delta);
        Vector3f newRotation = calculateRotation(delta);

        setPositionRotation(newPosition, newRotation);

        looking.set(0, 0);
    }

    @Override
    public RenderAssetContext getAssetContext() {
        return null;
    }

    private void pressSpace() {
        if (!isInAir) {
            upwardsSpeed = JUMP_POWER;
        } else {
            if (flyingEnabled) {
                isFlying = true;
                upwardsSpeed = JUMP_POWER;
            }
        }
        isInAir = true;
    }

    private void releaseSpace() {
        if (isFlying) {
            upwardsSpeed = 0;
        }
    }

    private void pressShift() {
        if (isInAir && isFlying) {
            upwardsSpeed = -JUMP_POWER;
        }
    }

    private void releaseShift() {
        if (isFlying) {
            upwardsSpeed = 0;
        }
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

    private Vector3f calculatePosition(final double delta) {
        // HORIZONTAL MOVEMENT
        Vector3f newPosition = new Vector3f(getPosition());
        Vector3f rotation = getRotation();

        float multiplier;
        if (movement.x != 0 && movement.y != 0) {
            multiplier = arguments.strafeSpeed * (float) delta;
        } else {
            multiplier = arguments.moveSpeed * (float) delta;
        }

        if (movement.x < 0) {
            newPosition.add((float) (multiplier * sin(toRadians(rotation.x - 90))), 0, (float) (multiplier * -cos(toRadians(rotation.x - 90))));
        } else if (movement.x > 0) {
            newPosition.sub((float) (multiplier * sin(toRadians(rotation.x - 90))), 0, (float) (multiplier * -cos(toRadians(rotation.x - 90))));
        }

        if (movement.y < 0) {
            newPosition.add((float) (multiplier * sin(toRadians(rotation.x))), 0, (float) (multiplier * -cos(toRadians(rotation.x))));
        } else if (movement.y > 0) {
            newPosition.sub((float) (multiplier * sin(toRadians(rotation.x))), 0, (float) (multiplier * -cos(toRadians(rotation.x))));
        }

        // VERTICAL MOVEMENT
        if (!isFlying) {
            upwardsSpeed += GRAVITY * delta;
        }

        newPosition.add(0, upwardsSpeed * (float) delta, 0);

        float terrainHeight = arguments.terrain != null ? arguments.terrain.getHeight(newPosition.x, newPosition.z) + CAMERA_HEIGHT : 0;

        if (newPosition.y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            isFlying = false;
            newPosition.set(newPosition.x, terrainHeight, newPosition.z);
        }

        return newPosition;
    }

    private Vector3f calculateRotation(final double delta) {
        Vector3f newRotation = new Vector3f(getRotation());

        newRotation.add(looking.x * arguments.lookSpeed * (float) delta, -looking.y * arguments.lookSpeed * (float) delta, 0.0f);

        if (newRotation.x > 360) {
            newRotation.sub(360, 0, 0);
        } else if (newRotation.x < 0) {
            newRotation.add(360, 0, 0);
        }

        if (newRotation.y > arguments.lookUpLimit) {
            newRotation.set(newRotation.x, arguments.lookUpLimit, newRotation.z);
        } else if (newRotation.y < arguments.lookDownLimit) {
            newRotation.set(newRotation.x, arguments.lookDownLimit, newRotation.z);
        }

        return newRotation;
    }
}
