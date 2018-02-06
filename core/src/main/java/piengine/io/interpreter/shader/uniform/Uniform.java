package piengine.io.interpreter.shader.uniform;


import piengine.io.interpreter.shader.Shader;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public abstract class Uniform<T> {

    protected final int location;
    private T cachedValue;

    public Uniform(final Shader shader, final String variable) {
        this.location = glGetUniformLocation(shader.programId, variable);
    }

    public void load(final T value) {
        if (!value.equals(cachedValue)) {
            cachedValue = copyValue(value);
            loadToShader(value);
        }
    }

    protected abstract void loadToShader(final T value);

    protected abstract T copyValue(final T value);
}
