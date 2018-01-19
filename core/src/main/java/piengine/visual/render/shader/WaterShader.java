package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.core.base.type.property.PropertyKeys;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.pointshadow.domain.PointShadow;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformInteger;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector3f;
import piengine.visual.shader.domain.uniform.struct.UniformFog;
import piengine.visual.shader.domain.uniform.struct.UniformLight;
import piengine.visual.shader.domain.uniform.struct.UniformPointShadow;
import piengine.visual.shader.domain.uniform.struct.UniformShadow;
import piengine.visual.shadow.domain.Shadow;

import java.util.List;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.visual.shader.domain.uniform.UniformFloat.uniformFloat;
import static piengine.visual.shader.domain.uniform.UniformInteger.uniformInteger;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector3f.uniformVector3f;
import static piengine.visual.shader.domain.uniform.struct.UniformFog.uniformFog;
import static piengine.visual.shader.domain.uniform.struct.UniformLight.uniformLight;
import static piengine.visual.shader.domain.uniform.struct.UniformPointShadow.uniformPointShadow;
import static piengine.visual.shader.domain.uniform.struct.UniformShadow.uniformShadow;

public class WaterShader extends Shader {

    private static final int LIGHT_COUNT = get(PropertyKeys.LIGHT_COUNT);

    private final UniformMatrix4f viewMatrix = uniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformInteger reflectionTexture = uniformInteger(this, "reflectionTexture");
    private final UniformInteger refractionTexture = uniformInteger(this, "refractionTexture");
    private final UniformInteger depthTexture = uniformInteger(this, "depthTexture");
    private final UniformVector3f cameraPosition = uniformVector3f(this, "cameraPosition");
    private final UniformFloat waveFactor = uniformFloat(this, "waveFactor");
    private final UniformFog fog = uniformFog(this, "fog");
    private final UniformLight[] lights = uniformLight(this, "lights", LIGHT_COUNT);
    private final UniformInteger[] shadowMaps = uniformInteger(this, "shadowMaps", LIGHT_COUNT);
    private final UniformShadow[] shadows = uniformShadow(this, "shadows", LIGHT_COUNT);
    private final UniformInteger[] pointShadowMaps = uniformInteger(this, "pointShadowMaps", LIGHT_COUNT);
    private final UniformPointShadow[] pointShadows = uniformPointShadow(this, "pointShadows", LIGHT_COUNT);

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

        for (int i = 0; i < LIGHT_COUNT; i++) {
            shadowMaps[i].load(textureIndex++);
        }
        for (int i = 0; i < LIGHT_COUNT; i++) {
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

    public WaterShader loadLights(final List<Light> value) {
        int lightCount = value.size();

        for (int i = 0; i < LIGHT_COUNT; i++) {
            Light light = i < lightCount ? value.get(i) : new Light(null);
            lights[i].load(light);
        }

        return this;
    }

    public WaterShader loadShadows(final List<Shadow> value) {
        int shadowCount = value.size();
        for (int i = 0; i < LIGHT_COUNT; i++) {
            Shadow shadow = i < shadowCount ? value.get(i) : null;
            shadows[i].load(shadow);
        }

        return this;
    }

    public WaterShader loadPointShadows(final List<PointShadow> value) {
        int pointShadowCount = value.size();
        for (int i = 0; i < LIGHT_COUNT; i++) {
            PointShadow pointShadow = i < pointShadowCount ? value.get(i) : null;
            pointShadows[i].load(pointShadow);
        }

        return this;
    }

    public WaterShader loadFog(final Fog value) {
        fog.load(value);

        return this;
    }
}
