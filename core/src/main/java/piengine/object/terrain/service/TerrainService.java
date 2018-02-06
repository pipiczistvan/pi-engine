package piengine.object.terrain.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.terrain.domain.TerrainDto;
import piengine.io.loader.terrain.loader.TerrainLoader;
import piengine.object.terrain.domain.Terrain;
import piengine.object.terrain.domain.TerrainKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

import static piengine.io.interpreter.vertexarray.VertexAttribute.COLOR;
import static piengine.io.interpreter.vertexarray.VertexAttribute.NORMAL;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class TerrainService extends SupplierService<TerrainKey, Terrain> {

    private final TerrainLoader terrainLoader;
    private final Map<TerrainKey, VertexArray> vertexArrayMap;

    @Wire
    public TerrainService(final TerrainLoader terrainLoader) {
        this.terrainLoader = terrainLoader;
        this.vertexArrayMap = new HashMap<>();
    }

    @Override
    public Terrain supply(final TerrainKey key) {
        TerrainDto terrain = terrainLoader.load(key);
        VertexArray vertexArray = vertexArrayMap.computeIfAbsent(key, k -> createVertexArray(terrain));

        return new Terrain(vertexArray, terrain.heights, key.position, key.scale);
    }

    @Override
    public void terminate() {
        vertexArrayMap.values().forEach(VertexArray::clear);
    }

    private VertexArray createVertexArray(final TerrainDto terrain) {
        return new VertexArray(terrain.indices.length)
                .bind()
                .attachIndexBuffer(terrain.indices)
                .attachVertexBuffer(VERTEX, terrain.vertices, 3)
                .attachVertexBuffer(COLOR, terrain.colors, 3)
                .attachVertexBuffer(NORMAL, terrain.normals, 3)
                .unbind();
    }
}
