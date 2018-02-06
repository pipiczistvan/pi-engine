package piengine.visual.lighting.directional.shadow.service;

import org.joml.Matrix4f;
import piengine.core.base.domain.Entity;
import piengine.io.loader.glsl.loader.GlslLoader;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.camera.domain.Camera;
import piengine.object.model.domain.Model;
import piengine.visual.lighting.directional.shadow.shader.DirectionalShadowShader;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_FRONT;

@Component
public class DirectionalShadowRenderService extends AbstractRenderService<DirectionalShadowShader, RenderWorldPlanContext> {

    @Wire
    public DirectionalShadowRenderService(final RenderInterpreter renderInterpreter, final GlslLoader glslLoader) {
        super(renderInterpreter, glslLoader);
    }

    @Override
    protected DirectionalShadowShader createShader() {
        return createShader("directionalShadowShader", DirectionalShadowShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        Matrix4f projectionViewMatrix = createProjectionView(context.currentCamera);

        renderInterpreter.setViewport(context.viewport);

        shader.start();
        shader.loadRenderStage(0);
        for (Model model : context.models) {
            Matrix4f transformationMatrix = createTransformation(projectionViewMatrix, model);
            shader.loadTransformationMatrix(transformationMatrix);
            draw(model.vao);
        }
        shader.loadRenderStage(1);
        for (AnimatedModel animatedModel : context.animatedModels) {
            Matrix4f transformationMatrix = createTransformation(projectionViewMatrix, animatedModel);
            shader.loadTransformationMatrix(transformationMatrix)
                    .loadJointTransforms(animatedModel.getJointTransforms());
            draw(animatedModel.vao);
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
        Matrix4f viewMatrix = camera.getView();
        projectionMatrix.mul(viewMatrix, projectionViewMatrix);

        return projectionViewMatrix;
    }

    private Matrix4f createTransformation(final Matrix4f projectionView, final Entity entity) {
        Matrix4f transformationMatrix = new Matrix4f();
        Matrix4f modelMatrix = entity.getTransformation();
        projectionView.mul(modelMatrix, transformationMatrix);

        return transformationMatrix;
    }
}
