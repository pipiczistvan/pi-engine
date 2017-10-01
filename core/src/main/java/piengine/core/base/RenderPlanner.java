package piengine.core.base;

import piengine.core.base.api.Renderable;
import piengine.object.entity.domain.Entity;
import piengine.visual.render.domain.RenderPlan;
import piengine.visual.render.manager.RenderManager;

public abstract class RenderPlanner<T extends RenderPlan> extends Entity implements Renderable {

    public T renderPlan;
    protected final RenderManager renderManager;

    public RenderPlanner(final RenderManager renderManager) {
        super();
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
