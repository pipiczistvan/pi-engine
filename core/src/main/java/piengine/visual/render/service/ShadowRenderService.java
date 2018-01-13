package piengine.visual.render.service;

import org.joml.Matrix4f;
import piengine.object.entity.domain.Entity;
import piengine.object.model.domain.Model;
import piengine.visual.camera.domain.Camera;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.ShadowShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_FRONT;

@Component
public class ShadowRenderService extends AbstractRenderService<ShadowShader, RenderWorldPlanContext> {

    @Wire
    public ShadowRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected ShadowShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("shadowShader")).castTo(ShadowShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        Matrix4f projectionViewMatrix = createProjectionView(context.camera);

        renderInterpreter.setViewport(context.viewport);
        shader.start();

        for (Model model : context.models) {
            Matrix4f transformationMatrix = createTransformation(projectionViewMatrix, model);
            shader.loadTransformationMatrix(transformationMatrix);
            draw(model.mesh.getDao());
        }

        shader.stop();
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withCullFace(GL_FRONT)
                .build();
    }

    private Matrix4f createProjectionView(final Camera camera) {
        Matrix4f projectionViewMatrix = new Matrix4f();
        Matrix4f projectionMatrix = camera.getProjection();
        Matrix4f viewMatrix = camera.view;
        projectionMatrix.mul(viewMatrix, projectionViewMatrix);

        return projectionViewMatrix;
    }

    private Matrix4f createTransformation(final Matrix4f projectionView, final Entity entity) {
        Matrix4f transformationMatrix = new Matrix4f();
        Matrix4f modelMatrix = entity.getModelMatrix();
        projectionView.mul(modelMatrix, transformationMatrix);

        return transformationMatrix;
    }
}
