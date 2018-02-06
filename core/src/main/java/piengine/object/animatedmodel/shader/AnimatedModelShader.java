package piengine.object.animatedmodel.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.core.base.type.property.PropertyKeys;
import piengine.io.interpreter.shader.Shader;
import piengine.io.interpreter.shader.uniform.UniformBoolean;
import piengine.io.interpreter.shader.uniform.UniformMatrix4f;
import piengine.io.interpreter.shader.uniform.UniformVector4f;
import piengine.io.interpreter.shader.uniform.struct.UniformDirectionalLight;
import piengine.io.interpreter.shader.uniform.struct.UniformFog;
import piengine.io.interpreter.shader.uniform.struct.UniformPointLight;
import piengine.io.loader.glsl.domain.GlslDto;
import piengine.visual.fog.Fog;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.point.light.domain.PointLight;

import java.util.List;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.ANIMATION_SKELETON_MAX_JOINTS;
import static piengine.io.interpreter.shader.uniform.UniformBoolean.uniformBoolean;
import static piengine.io.interpreter.shader.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.io.interpreter.shader.uniform.UniformVector4f.uniformVector4f;
import static piengine.io.interpreter.shader.uniform.struct.UniformDirectionalLight.uniformDirectionalLight;
import static piengine.io.interpreter.shader.uniform.struct.UniformFog.uniformFog;
import static piengine.io.interpreter.shader.uniform.struct.UniformPointLight.uniformPointLight;

public class AnimatedModelShader extends Shader {

    private static final int DIRECTIONAL_LIGHT_COUNT = get(PropertyKeys.LIGHTING_DIRECTIONAL_LIGHT_COUNT);
    private static final int POINT_LIGHT_COUNT = get(PropertyKeys.LIGHTING_POINT_LIGHT_COUNT);
    private static final int MAX_JOINTS = get(ANIMATION_SKELETON_MAX_JOINTS);

    private final UniformMatrix4f modelMatrix = uniformMatrix4f(this, "modelMatrix");
    private final UniformMatrix4f viewMatrix = uniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformVector4f clippingPlane = uniformVector4f(this, "clippingPlane");
    private final UniformBoolean lightEmitter = uniformBoolean(this, "lightEmitter");
    private final UniformFog fog = uniformFog(this, "fog");
    private final UniformDirectionalLight[] directionalLights = uniformDirectionalLight(this, "directionalLights", DIRECTIONAL_LIGHT_COUNT);
    private final UniformPointLight[] pointLights = uniformPointLight(this, "pointLights", POINT_LIGHT_COUNT);
    private UniformMatrix4f[] jointTransforms = uniformMatrix4f(this, "jointTransforms", MAX_JOINTS);

    public AnimatedModelShader(final GlslDto glsl) {
        super(glsl);
    }

    public AnimatedModelShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);

        return this;
    }

    public AnimatedModelShader loadViewMatrix(final Matrix4f value) {
        viewMatrix.load(value);

        return this;
    }

    public AnimatedModelShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public AnimatedModelShader loadDirectionalLights(final List<DirectionalLight> value) {
        int lightCount = value.size();

        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            if (i < lightCount) {
                DirectionalLight light = value.get(i);
                directionalLights[i].load(light);
            } else {
                directionalLights[i].load(null);
            }
        }

        return this;
    }

    public AnimatedModelShader loadPointLights(final List<PointLight> value) {
        int lightCount = value.size();

        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            if (i < lightCount) {
                PointLight light = value.get(i);
                pointLights[i].load(light);
            } else {
                pointLights[i].load(null);
            }
        }

        return this;
    }

    public AnimatedModelShader loadClippingPlane(final Vector4f value) {
        clippingPlane.load(value);

        return this;
    }

    public AnimatedModelShader loadFog(final Fog value) {
        fog.load(value);

        return this;
    }

    public AnimatedModelShader loadLightEmitter(final boolean value) {
        lightEmitter.load(value);

        return this;
    }

    public AnimatedModelShader loadJointTransforms(final Matrix4f[] value) {
        for (int i = 0; i < value.length; i++) {
            jointTransforms[i].load(value[i]);
        }

        return this;
    }
}
