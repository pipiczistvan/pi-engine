package piengine.visual.render.service;

import org.joml.Matrix4f;
import piengine.object.camera.domain.Camera;
import piengine.object.model.domain.Model;
import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.render.domain.config.RenderConfig;
import piengine.visual.render.domain.config.RenderConfigBuilder;
import piengine.visual.render.domain.fragment.domain.RenderWorldPlanContext;
import piengine.visual.render.interpreter.RenderInterpreter;
import piengine.visual.render.shader.PointShadowShader;
import piengine.visual.shader.domain.ShaderKey;
import piengine.visual.shader.service.ShaderService;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_FRONT;
import static piengine.visual.lighting.point.shadow.domain.PointShadow.CAMERA_COUNT;

@Component
public class PointShadowRenderService extends AbstractRenderService<PointShadowShader, RenderWorldPlanContext> {

    @Wire
    public PointShadowRenderService(final ShaderService shaderService, final RenderInterpreter renderInterpreter) {
        super(shaderService, renderInterpreter);
    }

    @Override
    protected PointShadowShader createShader(final ShaderService shaderService) {
        return shaderService.supply(new ShaderKey("pointShadowShader")).castTo(PointShadowShader.class);
    }

    @Override
    protected void render(final RenderWorldPlanContext context) {
        renderInterpreter.setViewport(context.viewport);

        PointShadow pointShadow = context.currentPointShadow;

        Matrix4f[] projectionViewMatrices = new Matrix4f[CAMERA_COUNT];
        for (int i = 0; i < CAMERA_COUNT; i++) {
            projectionViewMatrices[i] = createProjectionView(pointShadow.getCamera(i));
        }
        shader.loadProjectionViewMatrices(projectionViewMatrices)
                .loadLightPosition(pointShadow.getPosition());

        for (Model model : context.models) {
            if (!model.lightEmitter) {
                shader.loadModelMatrix(model.getTransformation());
                draw(model.mesh.getDao());
            }
        }
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
}
