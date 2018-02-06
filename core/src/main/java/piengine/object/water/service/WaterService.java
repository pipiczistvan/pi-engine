package piengine.object.water.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.framebuffer.Framebuffer;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.object.water.domain.Water;
import piengine.object.water.domain.WaterGrid;
import piengine.object.water.domain.WaterKey;
import piengine.object.water.generator.WaterGridGenerator;
import puppeteer.annotation.premade.Component;

import java.util.HashMap;
import java.util.Map;

import static piengine.io.interpreter.vertexarray.VertexAttribute.INDICATOR;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class WaterService extends SupplierService<WaterKey, Water> {

    private final WaterGridGenerator waterGridGenerator;
    private final Map<WaterKey, Water> waterMap;

    public WaterService() {
        this.waterGridGenerator = new WaterGridGenerator();
        this.waterMap = new HashMap<>();
    }

    @Override
    public Water supply(final WaterKey key) {
        return waterMap.computeIfAbsent(key, this::createWater);
    }

    @Override
    public void terminate() {
        waterMap.values().forEach(water -> {
            water.vao.clear();
            water.reflectionBuffer.clear();
            water.refractionBuffer.clear();
        });
    }

    private Water createWater(final WaterKey key) {
        VertexArray vertexArray = createVertexArray(key);
        Framebuffer reflectionBuffer = new Framebuffer(key.resolution.x, key.resolution.y)
                .bind()
                .attachColorTexture()
                .attachDepthBuffer()
                .unbind();
        Framebuffer refractionBuffer = new Framebuffer(key.resolution.x / 2, key.resolution.y / 2)
                .bind()
                .attachColorTexture()
                .attachDepthTexture()
                .unbind();

        return new Water(vertexArray, reflectionBuffer, refractionBuffer, key.position, key.rotation, key.scale, key.color);
    }

    private VertexArray createVertexArray(final WaterKey key) {
        WaterGrid grid = waterGridGenerator.generate(key.size, key.position, key.rotation, key.scale);

        // todo: mayba multiple ?
        return new VertexArray(grid.positions.length / 2)
                .bind()
                .attachVertexBuffer(VERTEX, grid.positions, 3)
                .attachVertexBuffer(INDICATOR, grid.indicators, 4)
                .unbind();
    }
}
