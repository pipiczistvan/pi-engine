package piengine.visual.lighting.point.shadow.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.visual.lighting.point.shadow.domain.PointShadow;
import piengine.visual.lighting.point.shadow.domain.PointShadowKey;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PointShadowService extends SupplierService<PointShadowKey, PointShadow> {

    private final List<PointShadow> pointShadows;

    public PointShadowService() {
        this.pointShadows = new ArrayList<>();
    }

    @Override
    public PointShadow supply(final PointShadowKey key) {
        Framebuffer shadowMap = new Framebuffer(key.resolution.x, key.resolution.y)
                .bind()
                .setReadBuffer(false)
                .setDrawBuffer(false)
                .attachDepthCubeMap()
                .unbind();

        PointShadow pointShadow = new PointShadow(shadowMap);
        pointShadows.add(pointShadow);

        return pointShadow;
    }

    @Override
    public void terminate() {
        pointShadows.forEach(pointShadow -> pointShadow.getShadowMap().clear());
    }
}
