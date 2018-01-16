package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformInteger;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector3f;
import piengine.visual.shader.domain.uniform.UniformVector4f;
import piengine.visual.shadow.domain.Shadow;

import java.util.List;

public class TerrainShader extends Shader {

    private final UniformMatrix4f viewMatrix = new UniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f(this, "projectionMatrix");
    private final UniformVector4f clippingPlane = new UniformVector4f(this, "clippingPlane");
    private final UniformColor fogColor = new UniformColor(this, "fogColor");
    private final UniformFloat fogDensity = new UniformFloat(this, "fogDensity");
    private final UniformFloat fogGradient = new UniformFloat(this, "fogGradient");
    private final UniformVector3f[] lightPositions = uniformVector3fArray("lights", "position", MAX_LIGHTS);
    private final UniformColor[] lightColors = uniformColorArray("lights", "color", MAX_LIGHTS);
    private final UniformVector3f[] lightAttenuations = uniformVector3fArray("lights", "attenuation", MAX_LIGHTS);
    private final UniformBoolean[] shadowEnableds = uniformBooleanArray("shadows", "enabled", MAX_LIGHTS);
    private final UniformInteger[] shadowShadowMaps = uniformIntegerArray("shadows", "shadowMap", MAX_LIGHTS);
    private final UniformMatrix4f[] shadowSpaceMatrices = uniformMatrix4fArray("shadows", "spaceMatrix", MAX_LIGHTS);

    public TerrainShader(final ShaderDao dao) {
        super(dao);
    }

    public TerrainShader start() {
        startShader();

        return this;
    }

    public TerrainShader stop() {
        stopShader();

        return this;
    }

    public TerrainShader loadViewMatrix(final Matrix4f value) {
        viewMatrix.load(value);

        return this;
    }

    public TerrainShader loadProjectionMatrix(final Matrix4f value) {
        projectionMatrix.load(value);

        return this;
    }

    public TerrainShader loadLights(final List<Light> lights) {
        int lightCount = lights.size();

        for (int i = 0; i < MAX_LIGHTS; i++) {
            Light light = i < lightCount ? lights.get(i) : new Light(null);
            lightPositions[i].load(light.getPosition());
            lightColors[i].load(light.getColor());
            lightAttenuations[i].load(light.getAttenuation());
        }

        return this;
    }

    public TerrainShader loadClippingPlane(final Vector4f value) {
        clippingPlane.load(value);

        return this;
    }

    public TerrainShader loadFog(final Fog fog) {
        fogColor.load(fog.color);
        fogDensity.load(fog.density);
        fogGradient.load(fog.gradient);

        return this;
    }

    public TerrainShader loadShadows(final List<Shadow> shadows) {
        int shadowCount = shadows.size();

        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < shadowCount) {
                shadowEnableds[i].load(true);
                shadowSpaceMatrices[i].load(shadows.get(i).spaceMatrix);
            } else {
                shadowEnableds[i].load(false);
            }
        }

        return this;
    }

    public TerrainShader loadTextureUnits(final List<Shadow> shadows) {
        int textureIndex = 0;

        while (textureIndex < shadows.size() && textureIndex < MAX_LIGHTS) {
            shadowShadowMaps[textureIndex].load(textureIndex++);
        }

        return this;
    }
}
