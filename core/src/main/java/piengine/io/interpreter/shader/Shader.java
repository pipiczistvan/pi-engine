package piengine.io.interpreter.shader;

import piengine.core.base.exception.PIEngineException;
import piengine.io.interpreter.Interpreter;
import piengine.io.loader.glsl.domain.GlslDto;

import java.lang.reflect.InvocationTargetException;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;

public abstract class Shader implements Interpreter {

    public final Integer programId;
    private final Integer vertexShaderId;
    private final Integer tessControlShaderId;
    private final Integer tessEvalShaderId;
    private final Integer geometryShaderId;
    private final Integer fragmentShaderId;

    public static <S extends Shader> S newInstance(final Class<S> shaderClass, final GlslDto glsl) {
        try {
            return shaderClass.getConstructor(GlslDto.class).newInstance(glsl);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new PIEngineException("Could not cast shader to shader class %s!", shaderClass.getName());
        }
    }

    public Shader(final GlslDto glsl) {
        this.vertexShaderId = createShader(glsl.vertexSource, GL_VERTEX_SHADER);
        this.tessControlShaderId = createShader(glsl.tessControlSource, GL_TESS_CONTROL_SHADER);
        this.tessEvalShaderId = createShader(glsl.tessEvalSource, GL_TESS_EVALUATION_SHADER);
        this.geometryShaderId = createShader(glsl.geometrySource, GL_GEOMETRY_SHADER);
        this.fragmentShaderId = createShader(glsl.fragmentSource, GL_FRAGMENT_SHADER);

        this.programId = glCreateProgram();
        attachShader(programId, vertexShaderId);
        attachShader(programId, tessControlShaderId);
        attachShader(programId, tessEvalShaderId);
        attachShader(programId, geometryShaderId);
        attachShader(programId, fragmentShaderId);

        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw new PIEngineException("Unable to link shader program %s", programId);
        }
        glValidateProgram(programId);
    }

    @Override
    public void clear() {
        stop();

        detachShader(programId, vertexShaderId);
        detachShader(programId, tessControlShaderId);
        detachShader(programId, tessEvalShaderId);
        detachShader(programId, geometryShaderId);
        detachShader(programId, fragmentShaderId);

        deleteShader(vertexShaderId);
        deleteShader(tessControlShaderId);
        deleteShader(tessEvalShaderId);
        deleteShader(geometryShaderId);
        deleteShader(fragmentShaderId);

        glDeleteProgram(programId);
    }

    public Shader start() {
        glUseProgram(programId);

        return this;
    }

    public Shader stop() {
        glUseProgram(0);

        return this;
    }

    private Integer createShader(final String shaderSource, final int type) {
        if (shaderSource == null) {
            return null;
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new PIEngineException("Could not compile shader.\n%s", glGetShaderInfoLog(shaderID, 500));
        }
        return shaderID;
    }

    private void attachShader(final Integer programId, final Integer shaderId) {
        if (shaderId != null) {
            glAttachShader(programId, shaderId);
        }
    }

    private void detachShader(final Integer programId, final Integer shaderId) {
        if (shaderId != null) {
            glDetachShader(programId, shaderId);
        }
    }

    private void deleteShader(final Integer shaderId) {
        if (shaderId != null) {
            glDeleteShader(shaderId);
        }
    }
}
