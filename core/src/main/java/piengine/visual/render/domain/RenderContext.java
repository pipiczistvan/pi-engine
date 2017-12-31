package piengine.visual.render.domain;

import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.common.gui.writing.text.domain.Text;
import piengine.object.model.domain.Model;
import piengine.visual.camera.Camera;
import piengine.visual.light.Light;
import piengine.visual.texture.domain.Texture;

import java.util.ArrayList;
import java.util.List;

public class RenderContext {

    public final List<Model> models = new ArrayList<>();
    public final List<Text> texts = new ArrayList<>();
    public Vector4f clearColor;
    public Vector2i viewport;
    public Texture texture;
    public Camera camera;
    public Light light;
    public Vector4f color;

}
