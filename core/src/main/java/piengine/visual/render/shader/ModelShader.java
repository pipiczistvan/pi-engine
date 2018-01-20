package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.core.base.type.color.Color;
import piengine.core.base.type.property.PropertyKeys;
import piengine.visual.fog.Fog;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector4f;
import piengine.visual.shader.domain.uniform.struct.UniformDirectionalLight;
import piengine.visual.shader.domain.uniform.struct.UniformFog;
import piengine.visual.shader.domain.uniform.struct.UniformPointLight;

import java.util.List;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector4f.uniformVector4f;
import static piengine.visual.shader.domain.uniform.struct.UniformDirectionalLight.uniformDirectionalLight;
import static piengine.visual.shader.domain.uniform.struct.UniformFog.uniformFog;
import static piengine.visual.shader.domain.uniform.struct.UniformPointLight.uniformPointLight;

public class ModelShader extends Shader {

    private static final int DIRECTIONAL_LIGHT_COUNT = get(PropertyKeys.LIGHTING_DIRECTIONAL_LIGHT_COUNT);
    private static final int POINT_LIGHT_COUNT = get(PropertyKeys.LIGHTING_POINT_LIGHT_COUNT);

    private final UniformMatrix4f modelMatrix = uniformMatrix4f(this, "modelMatrix");
    private final UniformMatrix4f viewMatrix = uniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformColor color = uniformColor(this, "color");
    private final UniformBoolean textureEnabled = uniformBoolean(this, "textureEnabled");
    private final UniformVector4f clippingPlane = uniformVector4f(this, "clippingPlane");
    private final UniformBoolean lightEmitter = uniformBoolean(this, "lightEmitter");
    private final UniformFog fog = uniformFog(this, "fog");
    private final UniformDirectionalLight[] directionalLights = uniformDirectionalLight(this, "directionalLights", DIRECTIONAL_LIGHT_COUNT);
    private final UniformPointLight[] pointLights = uniformPointLight(this, "pointLights", POINT_LIGHT_COUNT);

    public ModelShader(final ShaderDao dao) {
        super(dao);
    }

    public ModelShader start() {
        startShader();

        return this;
    }

    public ModelShader stop() {
        stopShader();

        return this;
    }

    public ModelShader loadModelMatrix(final Matrix4f value) {
        modelMatrix.load(value);

        return this;
    }

    public ModelShader loadViewMatrix(final Matrix4f value) {
        viewMatrix.load(value);

        return this;
    }

    public ModelShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public ModelShader loadDirectionalLights(final List<DirectionalLight> value) {
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

    public ModelShader loadPointLights(final List<PointLight> value) {
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

    public ModelShader loadColor(final Color value) {
        color.load(value);

        return this;
    }

    public ModelShader loadTextureEnabled(final boolean value) {
        textureEnabled.load(value);

        return this;
    }

    public ModelShader loadClippingPlane(final Vector4f value) {
        clippingPlane.load(value);

        return this;
    }

    public ModelShader loadFog(final Fog value) {
        fog.load(value);

        return this;
    }

    public ModelShader loadLightEmitter(final boolean value) {
        lightEmitter.load(value);

        return this;
    }
}
