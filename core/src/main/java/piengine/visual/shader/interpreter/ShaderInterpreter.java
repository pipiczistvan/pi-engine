package piengine.visual.shader.interpreter;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import piengine.core.base.api.Interpreter;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.type.color.Color;
import piengine.visual.shader.domain.ShaderDao;
import piengine.visual.shader.domain.ShaderData;
import puppeteer.annotation.premade.Component;

import java.nio.FloatBuffer;

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
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;

@Component
public class ShaderInterpreter implements Interpreter<ShaderData, ShaderDao> {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    @Override
    public ShaderDao create(final ShaderData shaderData) {
        Integer vertexShaderId = createShader(shaderData.vertexSource, GL_VERTEX_SHADER);
        Integer tessControlShaderId = createShader(shaderData.tessControlSource, GL_TESS_CONTROL_SHADER);
        Integer tessEvalShaderId = createShader(shaderData.tessEvalSource, GL_TESS_EVALUATION_SHADER);
        Integer geometryShaderId = createShader(shaderData.geometrySource, GL_GEOMETRY_SHADER);
        Integer fragmentShaderId = createShader(shaderData.fragmentSource, GL_FRAGMENT_SHADER);

        Integer programId = glCreateProgram();
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

        return new ShaderDao(programId, vertexShaderId, tessControlShaderId, tessEvalShaderId, geometryShaderId, fragmentShaderId);
    }

    public void loadInt(final int location, final int value) {
        glUniform1i(location, value);
    }

    public void loadFloat(final int location, final float value) {
        glUniform1f(location, value);
    }

    public void loadVector2(final int location, final Vector2f vector) {
        glUniform2f(location, vector.x, vector.y);
    }

    public void loadVector3(final int location, final Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public void loadVector4(final int location, final Vector4f vector) {
        glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    public void loadColor(final int location, final Color color) {
        glUniform4f(location, color.r, color.g, color.b, color.a);
    }

    public void loadBoolean(final int location, final boolean value) {
        glUniform1f(location, value ? 1 : 0);
    }

    public void loadMatrix4(final int location, final Matrix4f matrix) {
        matrix.get(matrixBuffer);
        glUniformMatrix4fv(location, false, matrixBuffer);
    }

    public int getUniformLocation(final ShaderDao dao, final String variable) {
        return glGetUniformLocation(dao.programId, variable);
    }

    public void startShader(final ShaderDao dao) {
        glUseProgram(dao.programId);
    }

    public void stopShader() {
        glUseProgram(0);
    }

    @Override
    public void free(final ShaderDao dao) {
        stopShader();

        detachShader(dao.programId, dao.vertexShaderId);
        detachShader(dao.programId, dao.tessControlShaderId);
        detachShader(dao.programId, dao.tessEvalShaderId);
        detachShader(dao.programId, dao.geometryShaderId);
        detachShader(dao.programId, dao.fragmentShaderId);

        deleteShader(dao.vertexShaderId);
        deleteShader(dao.tessControlShaderId);
        deleteShader(dao.tessEvalShaderId);
        deleteShader(dao.geometryShaderId);
        deleteShader(dao.fragmentShaderId);

        glDeleteProgram(dao.programId);
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
