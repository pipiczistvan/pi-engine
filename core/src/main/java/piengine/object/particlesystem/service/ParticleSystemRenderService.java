package piengine.object.particlesystem.service;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.object.camera.domain.Camera;
import piengine.object.entity.domain.Entity;
import piengine.object.particlesystem.domain.Particle;
import piengine.object.particlesystem.domain.ParticleSystem;
import piengine.object.particlesystem.shader.ParticleSystemShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class ParticleSystemRenderService extends AbstractRenderService<ParticleSystemShader, RenderWorldPlanContext> {

    @Wire
    public ParticleSystemRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected ParticleSystemShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("particleSystemShader")).castTo(ParticleSystemShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        shader.loadProjectionMatrix(context.currentCamera.getProjection());

        for (ParticleSystem particleSystem : context.particleSystems) {
            for (Particle particle : particleSystem.particles) {
                shader.loadModelViewMatrix(createModelView(particle, context.currentCamera));
                draw(particleSystem.mesh.getDao());
            }
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

    //todo: temporary solution
    private Matrix4f createModelView(final Entity entity, final Camera camera) {
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

        return transformationMatrix;
    }
}
