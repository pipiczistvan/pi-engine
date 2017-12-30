package piengine.visual.render.domain;

import org.joml.Vector4f;
import piengine.common.gui.writing.text.domain.Text;
import piengine.common.planet.domain.Planet;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.visual.camera.Camera;
import piengine.visual.light.Light;
import piengine.visual.texture.domain.Texture;

import java.util.ArrayList;
import java.util.List;

public class RenderContext {

    public final Vector4f clearColor = new Vector4f();
    public final List<Model> models = new ArrayList<>();
    public final List<Text> texts = new ArrayList<>();
    public Texture texture;
    public Camera camera;
    public Light light;
    public Planet planet;
    public Vector4f color;

}
