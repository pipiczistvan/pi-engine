package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

public class CanvasShader extends Shader {

    private final UniformMatrix4f modelMatrix = new UniformMatrix4f(this, "modelMatrix");
    private final UniformColor color = new UniformColor(this, "color");
    private final UniformBoolean textureEnabled = new UniformBoolean(this, "textureEnabled");

    public CanvasShader(final ShaderDao dao) {
        super(dao);
    }

    public CanvasShader start() {
        startShader();

        return this;
    }

    public CanvasShader stop() {
        stopShader();

        return this;
    }

    public CanvasShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);

        return this;
    }

    public CanvasShader loadColor(final Color value) {
        color.load(value);

        return this;
    }

    public CanvasShader loadTextureEnabled(final boolean value) {
        textureEnabled.load(value);

        return this;
    }

}
