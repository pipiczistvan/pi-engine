package piengine.core.base.type.property;

import java.util.HashSet;
import java.util.Set;

public class PropertyKeys {

    static final Set<String> KEYS = new HashSet<>();

    public static final BooleanProperty DISPLAY_IN_CANVAS = new BooleanProperty("display.in.canvas");
    public static final IntegerProperty DISPLAY_MAJOR_VERSION = new IntegerProperty("display.major.version");
    public static final IntegerProperty DISPLAY_MINOR_VERSION = new IntegerProperty("display.minor.version");

    public static final StringProperty WINDOW_TITLE = new StringProperty("window.title");
    public static final IntegerProperty WINDOW_MIN_WIDTH = new IntegerProperty("window.min.width");
    public static final IntegerProperty WINDOW_MIN_HEIGHT = new IntegerProperty("window.min.height");
    public static final IntegerProperty WINDOW_WIDTH = new IntegerProperty("window.width");
    public static final IntegerProperty WINDOW_HEIGHT = new IntegerProperty("window.height");
    public static final BooleanProperty WINDOW_FULL_SCREEN = new BooleanProperty("window.full.screen");
    public static final BooleanProperty WINDOW_RESIZABLE = new BooleanProperty("window.resizable");
    public static final BooleanProperty WINDOW_CURSOR_HIDDEN = new BooleanProperty("window.cursor.hidden");

    public static final IntegerProperty TIME_FPS_CAP = new IntegerProperty("time.fps.cap");

    public static final StringProperty RESOURCES_LOCATION = new StringProperty("engine.resources.root");
    public static final StringProperty SHADERS_LOCATION = new StringProperty("engine.resources.root.shaders");
    public static final StringProperty MESHES_LOCATION = new StringProperty("engine.resources.root.meshes");
    public static final StringProperty IMAGES_LOCATION = new StringProperty("engine.resources.root.images");
    public static final StringProperty FONTS_LOCATION = new StringProperty("engine.resources.root.fonts");
    public static final StringProperty COLLADA_LOCATION = new StringProperty("engine.resources.root.collada");

    public static final FloatProperty CAMERA_FOV = new FloatProperty("camera.fov");
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
    public static final FloatProperty LIGHTING_DIRECTIONAL_SHADOW_TRANSITION_DISTANCE = new FloatProperty("lighting.directional.shadow.transition.distance");
    public static final IntegerProperty LIGHTING_POINT_LIGHT_COUNT = new IntegerProperty("lighting.point.light.count");
    public static final FloatProperty LIGHTING_POINT_SHADOW_DISTANCE = new FloatProperty("lighting.point.shadow.distance");
    public static final FloatProperty LIGHTING_POINT_SHADOW_TRANSITION_DISTANCE = new FloatProperty("lighting.point.shadow.transition.distance");

    public static final IntegerProperty ANIMATION_SKELETON_MAX_WEIGHTS = new IntegerProperty("animation.skeleton.max.weights");
    public static final IntegerProperty ANIMATION_SKELETON_MAX_JOINTS = new IntegerProperty("animation.skeleton.max.joints");

    public static final FloatProperty WATER_WAVE_SPEED = new FloatProperty("water.wave.speed");
    public static final FloatProperty WATER_WAVE_LENGTH = new FloatProperty("water.wave.length");
    public static final FloatProperty WATER_WAVE_AMPLITUDE = new FloatProperty("water.wave.amplitude");
    public static final FloatProperty WATER_FRESNEL_EFFECT = new FloatProperty("water.fresnel.effect");
    public static final FloatProperty WATER_EDGE_SOFTNESS = new FloatProperty("water.edge.softness");
    public static final FloatProperty WATER_COLOR_MIN = new FloatProperty("water.color.min");
    public static final FloatProperty WATER_COLOR_MAX = new FloatProperty("water.color.max");
    public static final FloatProperty WATER_MURKY_DEPTH = new FloatProperty("water.murky.depth");
}
