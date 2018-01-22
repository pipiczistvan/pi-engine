package piengine.visual.lighting.directional.shadow.shader;

import org.joml.Matrix4f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;

import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;

public class DirectionalShadowShader extends Shader {

    private final UniformMatrix4f transformationMatrix = uniformMatrix4f(this, "transformationMatrix");

    public DirectionalShadowShader(final ShaderDao dao) {
        super(dao);
    }

    public DirectionalShadowShader loadTransformationMatrix(final Matrix4f value) {
        transformationMatrix.load(value);

        return this;
    }
}
