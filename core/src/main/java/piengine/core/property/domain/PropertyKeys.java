package piengine.core.property.domain;

import java.util.HashSet;
import java.util.Set;

public class PropertyKeys {

    static final Set<String> KEYS = new HashSet<>();

    public static final StringProperty WINDOW_TITLE = new StringProperty("window.title");
    public static final IntegerProperty WINDOW_WIDTH = new IntegerProperty("window.width");
    public static final IntegerProperty WINDOW_HEIGHT = new IntegerProperty("window.height");
    public static final BooleanProperty WINDOW_FULL_SCREEN = new BooleanProperty("window.full.screen");
    public static final IntegerProperty WINDOW_MULTI_SAMPLE_COUNT = new IntegerProperty("window.multi.sample.count");
    public static final BooleanProperty WINDOW_CURSOR_HIDDEN = new BooleanProperty("window.cursor.hidden");
    public static final IntegerProperty WINDOW_MAJOR_VERSION = new IntegerProperty("window.major.version");
    public static final IntegerProperty WINDOW_MINOR_VERSION = new IntegerProperty("window.minor.version");

    public static final IntegerProperty TIME_FPS_CAP = new IntegerProperty("time.fps.cap");

    public static final StringProperty SHADERS_LOCATION = new StringProperty("engine.resources.root.shaders");
    public static final StringProperty MESHES_LOCATION = new StringProperty("engine.resources.root.meshes");
    public static final StringProperty IMAGES_LOCATION = new StringProperty("engine.resources.root.images");
    public static final StringProperty FONTS_LOCATION = new StringProperty("engine.resources.root.fonts");

    public static final FloatProperty CAMERA_FOV = new FloatProperty("camera.fov");
    public static final IntegerProperty CAMERA_VIEWPORT_WIDTH = new IntegerProperty("camera.viewport.width");
    public static final IntegerProperty CAMERA_VIEWPORT_HEIGHT = new IntegerProperty("camera.viewport.height");
    public static final FloatProperty CAMERA_NEAR_PLANE = new FloatProperty("camera.near.plane");
    public static final FloatProperty CAMERA_FAR_PLANE = new FloatProperty("camera.far.plane");
    public static final FloatProperty CAMERA_LOOK_UP_LIMIT = new FloatProperty("camera.look.up.limit");
    public static final FloatProperty CAMERA_LOOK_DOWN_LIMIT = new FloatProperty("camera.look.down.limit");
    public static final FloatProperty CAMERA_LOOK_SPEED = new FloatProperty("camera.look.speed");
    public static final FloatProperty CAMERA_MOVE_SPEED = new FloatProperty("camera.move.speed");
    public static final FloatProperty CAMERA_DISTANCE = new FloatProperty("camera.distance");

}
