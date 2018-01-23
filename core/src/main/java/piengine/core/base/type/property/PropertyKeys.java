package piengine.core.base.type.property;

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

    public static final StringProperty RESOURCES_LOCATION = new StringProperty("engine.resources.root");
    public static final StringProperty SHADERS_LOCATION = new StringProperty("engine.resources.root.shaders");
    public static final StringProperty MESHES_LOCATION = new StringProperty("engine.resources.root.meshes");
    public static final StringProperty IMAGES_LOCATION = new StringProperty("engine.resources.root.images");
    public static final StringProperty FONTS_LOCATION = new StringProperty("engine.resources.root.fonts");
    public static final StringProperty COLLADA_LOCATION = new StringProperty("engine.resources.root.collada");

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

    public static final FloatProperty LIGHTING_SHADOW_DARKNESS = new FloatProperty("lighting.shadow.darkness");
    public static final IntegerProperty LIGHTING_DIRECTIONAL_LIGHT_COUNT = new IntegerProperty("lighting.directional.light.count");
    public static final FloatProperty LIGHTING_DIRECTIONAL_SHADOW_DISTANCE = new FloatProperty("lighting.directional.shadow.distance");
    public static final FloatProperty LIGHTING_DIRECTIONAL_SHADOW_OFFSET = new FloatProperty("lighting.directional.shadow.offset");
    public static final IntegerProperty LIGHTING_DIRECTIONAL_SHADOW_PCF_COUNT = new IntegerProperty("lighting.directional.shadow.pcf.count");
    public static final IntegerProperty LIGHTING_POINT_LIGHT_COUNT = new IntegerProperty("lighting.point.light.count");
    public static final FloatProperty LIGHTING_POINT_SHADOW_DISTANCE = new FloatProperty("lighting.point.shadow.distance");
}
