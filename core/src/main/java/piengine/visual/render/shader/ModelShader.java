package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.core.base.type.color.Color;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformFloat;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector3f;
import piengine.visual.shader.domain.uniform.UniformVector4f;

import java.util.List;

public class ModelShader extends Shader {

    private final UniformMatrix4f modelMatrix = new UniformMatrix4f(this, "modelMatrix");
    private final UniformMatrix4f viewMatrix = new UniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = new UniformMatrix4f(this, "projectionMatrix");
    private final UniformColor color = new UniformColor(this, "color");
    private final UniformBoolean textureEnabled = new UniformBoolean(this, "textureEnabled");
    private final UniformVector4f clippingPlane = new UniformVector4f(this, "clippingPlane");
    private final UniformColor fogColor = new UniformColor(this, "fogColor");
    private final UniformFloat fogDensity = new UniformFloat(this, "fogDensity");
    private final UniformFloat fogGradient = new UniformFloat(this, "fogGradient");
    private final UniformVector3f[] lightPositions = uniformVector3fArray("lights", "position", MAX_LIGHTS);
    private final UniformColor[] lightColors = uniformColorArray("lights", "color", MAX_LIGHTS);
    private final UniformVector3f[] lightAttenuations = uniformVector3fArray("lights", "attenuation", MAX_LIGHTS);

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

    public ModelShader loadLights(final List<Light> lights) {
        int lightCount = lights.size();

        for (int i = 0; i < MAX_LIGHTS; i++) {
            Light light = i < lightCount ? lights.get(i) : new Light(null);
            lightPositions[i].load(light.getPosition());
            lightColors[i].load(light.getColor());
            lightAttenuations[i].load(light.getAttenuation());
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

    public ModelShader loadFog(final Fog fog) {
        fogColor.load(fog.color);
        fogDensity.load(fog.density);
        fogGradient.load(fog.gradient);

        return this;
    }
}
