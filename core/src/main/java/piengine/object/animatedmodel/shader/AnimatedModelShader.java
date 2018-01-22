package piengine.object.animatedmodel.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector3f;

import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector3f.uniformVector3f;

public class AnimatedModelShader extends Shader {

    private static final int MAX_JOINTS = 50;

    private UniformMatrix4f projectionViewMatrix = uniformMatrix4f(this, "projectionViewMatrix");
    private UniformVector3f lightDirection = uniformVector3f(this, "lightDirection");
    private UniformMatrix4f[] jointTransforms = uniformMatrix4f(this, "jointTransforms", MAX_JOINTS);

    public AnimatedModelShader(final ShaderDao dao) {
        super(dao);
    }

    public AnimatedModelShader loadProjectionViewMatrix(final Matrix4f value) {
        projectionViewMatrix.load(value);

        return this;
    }

    public AnimatedModelShader loadLightDirection(final Vector3f value) {
        lightDirection.load(value);

        return this;
    }

    public AnimatedModelShader loadJointTransforms(final Matrix4f[] value) {
        for (int i = 0; i < value.length; i++) {
            jointTransforms[i].load(value[i]);
        }

        return this;
    }
}
