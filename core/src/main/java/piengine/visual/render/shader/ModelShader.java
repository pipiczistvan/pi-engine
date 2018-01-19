package piengine.visual.render.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import piengine.core.base.type.color.Color;
import piengine.core.base.type.property.PropertyKeys;
import piengine.visual.fog.Fog;
import piengine.visual.light.domain.Light;
import piengine.visual.shader.domain.Shader;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.uniform.UniformBoolean;
import piengine.visual.shader.domain.uniform.UniformColor;
import piengine.visual.shader.domain.uniform.UniformMatrix4f;
import piengine.visual.shader.domain.uniform.UniformVector4f;
import piengine.visual.shader.domain.uniform.struct.UniformFog;
import piengine.visual.shader.domain.uniform.struct.UniformLight;

import java.util.List;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.visual.shader.domain.uniform.UniformBoolean.uniformBoolean;
import static piengine.visual.shader.domain.uniform.UniformColor.uniformColor;
import static piengine.visual.shader.domain.uniform.UniformMatrix4f.uniformMatrix4f;
import static piengine.visual.shader.domain.uniform.UniformVector4f.uniformVector4f;
import static piengine.visual.shader.domain.uniform.struct.UniformFog.uniformFog;
import static piengine.visual.shader.domain.uniform.struct.UniformLight.uniformLight;

public class ModelShader extends Shader {

    private static final int LIGHT_COUNT = get(PropertyKeys.LIGHT_COUNT);

    private final UniformMatrix4f modelMatrix = uniformMatrix4f(this, "modelMatrix");
    private final UniformMatrix4f viewMatrix = uniformMatrix4f(this, "viewMatrix");
    private final UniformMatrix4f projectionMatrix = uniformMatrix4f(this, "projectionMatrix");
    private final UniformColor color = uniformColor(this, "color");
    private final UniformBoolean textureEnabled = uniformBoolean(this, "textureEnabled");
    private final UniformVector4f clippingPlane = uniformVector4f(this, "clippingPlane");
    private final UniformBoolean lightEmitter = uniformBoolean(this, "lightEmitter");
    private final UniformFog fog = uniformFog(this, "fog");
    private final UniformLight[] lights = uniformLight(this, "lights", LIGHT_COUNT);

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

    public ModelShader loadLights(final List<Light> value) {
        int lightCount = value.size();

        for (int i = 0; i < LIGHT_COUNT; i++) {
            Light light = i < lightCount ? value.get(i) : new Light(null);
            lights[i].load(light);
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
