package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

public class PointShadowShader extends Shader {

    private final UniformMatrix4f transformationMatrix = new UniformMatrix4f(this, "transformationMatrix");

    public PointShadowShader(final ShaderDao dao) {
        super(dao);
    }

    public PointShadowShader start() {
        startShader();
        return this;
    }

    public PointShadowShader stop() {
        stopShader();
        return this;
    }

    public PointShadowShader loadTransformationMatrix(final Matrix4f value) {
        transformationMatrix.load(value);
        return this;
    }
}
