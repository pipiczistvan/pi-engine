package piengine.object.particlesystem.service;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;
import piengine.core.base.domain.Entity;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.camera.domain.Camera;
import piengine.object.particlesystem.domain.Particle;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.shader.ParticleSystemShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.nio.FloatBuffer;

import static piengine.io.interpreter.vertexarray.VertexAttribute.EMPTY;
import static piengine.object.particlesystem.service.ParticleSystemService.INSTANCE_DATA_LENGTH;
import static piengine.object.particlesystem.service.ParticleSystemService.MAX_INSTANCES;

@Component
public class ParticleSystemRenderService extends AbstractRenderService<ParticleSystemShader, RenderWorldPlanContext> {

    private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);

    private int pointer = 0;

    @Wire
    public ParticleSystemRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected ParticleSystemShader createShader() {
        return createShader("particleSystemShader", ParticleSystemShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        shader.loadProjectionMatrix(context.currentCamera.getProjection());

        for (ParticleSystem particleSystem : context.particleSystems) {
            shader.loadNumberOfRows(particleSystem.getSprite().numberOfRows);
            particleSystem.getSprite().bind(GL13.GL_TEXTURE0);

            pointer = 0;
            float[] vboData = new float[particleSystem.getParticles().size() * INSTANCE_DATA_LENGTH];
            for (Particle particle : particleSystem.getParticles()) {
                updateModelView(particle, context.currentCamera, vboData);
                updateTextureInfo(particle, vboData);
            }
            particleSystem.getVao().vertexBuffers.get(EMPTY).update(vboData, buffer);
            drawInstanced(particleSystem.getVao(), particleSystem.getParticles().size());
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .withBlendTest(true)
                .withDepthMask(false)
                .build();
    }

    private void updateTextureInfo(final Particle particle, final float[] vboData) {
        vboData[pointer++] = particle.getTextureOffsetCurrent().x;
        vboData[pointer++] = particle.getTextureOffsetCurrent().y;
        vboData[pointer++] = particle.getTextureOffsetNext().x;
        vboData[pointer++] = particle.getTextureOffsetNext().y;
        vboData[pointer++] = particle.getTextureInfo().y;
    }

    //todo: temporary solution
    private void updateModelView(final Entity entity, final Camera camera, final float[] vboData) {
        Matrix4f transformationMatrix = new Matrix4f();

        Matrix4f viewMatrix = camera.getView();

        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(entity.getPosition());
        modelMatrix.m00(viewMatrix.m00());
        modelMatrix.m01(viewMatrix.m10());
        modelMatrix.m02(viewMatrix.m20());
        modelMatrix.m10(viewMatrix.m01());
        modelMatrix.m11(viewMatrix.m11());
        modelMatrix.m12(viewMatrix.m21());
        modelMatrix.m20(viewMatrix.m02());
        modelMatrix.m21(viewMatrix.m12());
        modelMatrix.m22(viewMatrix.m22());
        modelMatrix.rotate((float) Math.toRadians(entity.getRotation().z), new Vector3f(0, 0, 1));
        modelMatrix.scale(entity.getScale());

        viewMatrix.mul(modelMatrix, transformationMatrix);

        storeMatrixData(transformationMatrix, vboData);
    }

    private void storeMatrixData(final Matrix4f matrix, final float[] vboData) {
        vboData[pointer++] = matrix.m00();
        vboData[pointer++] = matrix.m01();
        vboData[pointer++] = matrix.m02();
        vboData[pointer++] = matrix.m03();
        vboData[pointer++] = matrix.m10();
        vboData[pointer++] = matrix.m11();
        vboData[pointer++] = matrix.m12();
        vboData[pointer++] = matrix.m13();
        vboData[pointer++] = matrix.m20();
        vboData[pointer++] = matrix.m21();
        vboData[pointer++] = matrix.m22();
        vboData[pointer++] = matrix.m23();
        vboData[pointer++] = matrix.m30();
        vboData[pointer++] = matrix.m31();
        vboData[pointer++] = matrix.m32();
        vboData[pointer++] = matrix.m33();
    }
}
