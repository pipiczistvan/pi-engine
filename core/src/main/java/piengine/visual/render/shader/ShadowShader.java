package piengine.visual.render.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

public class ShadowShader extends Shader {

    private final UniformMatrix4f transformationMatrix = new UniformMatrix4f(this, "transformationMatrix");

    public ShadowShader(final ShaderDao dao) {
        super(dao);
    }

    public ShadowShader start() {
        startShader();

        return this;
    }

    public ShadowShader stop() {
        stopShader();

        return this;
    }

    public ShadowShader loadTransformationMatrix(final Matrix4f value) {
        transformationMatrix.load(value);

        return this;
    }
}
