package piengine.object.animatedmodel.service;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.shader.AnimatedModelShader;
import piengine.object.camera.domain.Camera;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.service.AbstractRenderService;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import piengine.visual.texture.service.TextureService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class AnimatedModelRenderService extends AbstractRenderService<AnimatedModelShader, RenderWorldPlanContext> {

    private final TextureService textureService;

    @Wire
    public AnimatedModelRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter,
                                      final TextureService textureService) {
        super(shaderService, renderInterpreter);

        this.textureService = textureService;
    }

    @Override
    protected AnimatedModelShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("animatedModelShader")).castTo(AnimatedModelShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        shader.loadLightDirection(new Vector3f(1000, 1000, 300).normalize().negate())
                .loadProjectionViewMatrix(createProjectionView(context.currentCamera));

        for (AnimatedModel animatedModel : context.animatedModels) {
            shader.loadJointTransforms(animatedModel.getJointTransforms());
            textureService.bind(animatedModel.texture);
            draw(animatedModel.getDao());
        }
    }

    @Override
    protected RenderConfig createRenderConfig() {
        return RenderConfigBuilder.create()
                .withClipDistance(true)
                .build();
    }

    private Matrix4f createProjectionView(final Camera camera) {
        Matrix4f projectionViewMatrix = new Matrix4f();
        Matrix4f projectionMatrix = camera.getProjection();
        Matrix4f viewMatrix = camera.getView();
        projectionMatrix.mul(viewMatrix, projectionViewMatrix);

        return projectionViewMatrix;
    }
}
