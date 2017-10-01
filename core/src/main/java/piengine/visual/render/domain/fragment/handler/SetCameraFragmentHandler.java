package piengine.visual.render.domain.fragment.handler;

import piengine.visual.camera.Camera;
import piengine.visual.render.domain.RenderContext;
import piengine.visual.render.domain.fragment.domain.RenderFragmentType;
import puppeteer.annotation.premade.Component;

import static piengine.visual.render.domain.fragment.domain.RenderFragmentType.SET_CAMERA;

@Component
public class SetCameraFragmentHandler extends FragmentHandler<Camera> {

    @Override
    public void handle(final RenderContext context, final Camera camera) {
        context.camera = camera;
    }

    @Override
    public RenderFragmentType getType() {
        return SET_CAMERA;
    }

}
