package piengine.core.base;

import piengine.core.base.api.Renderable;
import piengine.object.entity.domain.Entity;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.manager.RenderManager;

public abstract class RenderPlanner<T extends RenderPlan> extends Entity implements Renderable {

    private T renderPlan;
    private final RenderManager renderManager;

    public RenderPlanner(final RenderManager renderManager) {
        super(null);
        this.renderManager = renderManager;
    }

    protected abstract T createRenderPlan();

    public void setupRenderPlan() {
        renderPlan = createRenderPlan();
    }

    @Override
    public void render() {
        renderManager.render(renderPlan);
    }

}
