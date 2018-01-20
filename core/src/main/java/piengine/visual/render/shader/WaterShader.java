package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.core.base.type.property.PropertyKeys;
import piengine.visual.fog.Fog;
import piengine.visual.lighting.directional.light.domain.DirectionalLight;
import piengine.visual.lighting.point.light.domain.PointLight;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformInteger;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector3f;
import piengine.visual.shader.domain.uniform.struct.UniformDirectionalLight;
import piengine.visual.shader.domain.uniform.struct.UniformDirectionalShadow;
import piengine.visual.shader.domain.uniform.struct.UniformFog;
import piengine.visual.shader.domain.uniform.struct.UniformPointLight;
import piengine.visual.shader.domain.uniform.struct.UniformPointShadow;

import java.util.List;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;
import static piengine.visual.shader.domain.uniform.UniformInteger.uniformInteger;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector3f.uniformVector3f;
import static piengine.visual.shader.domain.uniform.struct.UniformDirectionalLight.uniformDirectionalLight;
import static piengine.visual.shader.domain.uniform.struct.UniformDirectionalShadow.uniformShadow;
import static piengine.visual.shader.domain.uniform.struct.UniformFog.uniformFog;
import static piengine.visual.shader.domain.uniform.struct.UniformPointLight.uniformPointLight;
import static piengine.visual.shader.domain.uniform.struct.UniformPointShadow.uniformPointShadow;

public class WaterShader extends Shader {

    private static final int DIRECTIONAL_LIGHT_COUNT = get(PropertyKeys.LIGHTING_DIRECTIONAL_LIGHT_COUNT);
    private static final int POINT_LIGHT_COUNT = get(PropertyKeys.LIGHTING_POINT_LIGHT_COUNT);

    private final UniformMatrix4f viewMatrix = uniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformInteger reflectionTexture = uniformInteger(this, "reflectionTexture");
    private final UniformInteger refractionTexture = uniformInteger(this, "refractionTexture");
    private final UniformInteger depthTexture = uniformInteger(this, "depthTexture");
    private final UniformVector3f cameraPosition = uniformVector3f(this, "cameraPosition");
    private final UniformFloat waveFactor = uniformFloat(this, "waveFactor");
    private final UniformFog fog = uniformFog(this, "fog");
    private final UniformDirectionalLight[] directionalLights = uniformDirectionalLight(this, "directionalLights", DIRECTIONAL_LIGHT_COUNT);
    private final UniformInteger[] directionalShadowMaps = uniformInteger(this, "directionalShadowMaps", DIRECTIONAL_LIGHT_COUNT);
    private final UniformDirectionalShadow[] directionalShadows = uniformShadow(this, "directionalShadows", DIRECTIONAL_LIGHT_COUNT);
    private final UniformPointLight[] pointLights = uniformPointLight(this, "pointLights", POINT_LIGHT_COUNT);
    private final UniformInteger[] pointShadowMaps = uniformInteger(this, "pointShadowMaps", POINT_LIGHT_COUNT);
    private final UniformPointShadow[] pointShadows = uniformPointShadow(this, "pointShadows", POINT_LIGHT_COUNT);

    public WaterShader(final ShaderDao dao) {
        super(dao);
    }

    public WaterShader start() {
        startShader();
        return this;
    }

    public WaterShader stop() {
        stopShader();
        return this;
    }

    public WaterShader loadViewMatrix(final Matrix4f value) {
        viewMatrix.load(value);
        return this;
    }

    public WaterShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);
        return this;
    }

    public WaterShader loadTextureUnits() {
        int textureIndex = 0;

        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            directionalShadowMaps[i].load(textureIndex++);
        }
        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            pointShadowMaps[i].load(textureIndex++);
        }
        reflectionTexture.load(textureIndex++);
        refractionTexture.load(textureIndex++);
        depthTexture.load(textureIndex++);
        return this;
    }

    public WaterShader loadCameraPosition(final Vector3f value) {
        cameraPosition.load(value);
        return this;
    }

    public WaterShader loadWaveFactor(final float value) {
        waveFactor.load(value);
        return this;
    }

    public WaterShader loadDirectionalLights(final List<DirectionalLight> value) {
        int lightCount = value.size();

        for (int i = 0; i < DIRECTIONAL_LIGHT_COUNT; i++) {
            if (i < lightCount) {
                DirectionalLight light = value.get(i);
                directionalLights[i].load(light);
                directionalShadows[i].load(light.getShadow());
            } else {
                directionalLights[i].load(null);
                directionalShadows[i].load(null);
            }
        }

        return this;
    }

    public WaterShader loadPointLights(final List<PointLight> value) {
        int lightCount = value.size();

        for (int i = 0; i < POINT_LIGHT_COUNT; i++) {
            if (i < lightCount) {
                PointLight light = value.get(i);
                pointLights[i].load(light);
                pointShadows[i].load(light.getShadow());
            } else {
                pointLights[i].load(null);
                pointShadows[i].load(null);
            }
        }

        return this;
    }

    public WaterShader loadFog(final Fog value) {
        fog.load(value);

        return this;
    }
}
