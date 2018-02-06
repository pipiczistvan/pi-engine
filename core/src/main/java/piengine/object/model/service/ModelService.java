package piengine.object.model.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.obj.domain.ObjDto;
import piengine.io.loader.obj.loader.ObjLoader;
import piengine.object.model.domain.Model;
import piengine.object.model.domain.ModelKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

import static piengine.io.interpreter.vertexarray.VertexAttribute.NORMAL;
import static piengine.io.interpreter.vertexarray.VertexAttribute.TEXTURE_COORD;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;

@Component
public class ModelService extends SupplierService<ModelKey, Model> {

    private final ObjLoader objLoader;
    private final Map<String, VertexArray> vertexArrayMap;

    @Wire
    public ModelService(final ObjLoader objLoader) {
        this.objLoader = objLoader;
        this.vertexArrayMap = new HashMap<>();
    }

    @Override
    public Model supply(final ModelKey key) {
        VertexArray vertexArray = vertexArrayMap.computeIfAbsent(key.objFile, this::createVertexArray);

        return new Model(key.parent, vertexArray, key.texture, key.color, key.lightEmitter);
    }

    @Override
    public void terminate() {
        vertexArrayMap.values().forEach(VertexArray::clear);
    }

    private VertexArray createVertexArray(final String objFile) {
        ObjDto obj = objLoader.load(objFile);

        return new VertexArray(obj.indices.length)
                .bind()
                .attachIndexBuffer(obj.indices)
                .attachVertexBuffer(VERTEX, obj.vertices, 3)
                .attachVertexBuffer(TEXTURE_COORD, obj.textureCoords, 2)
                .attachVertexBuffer(NORMAL, obj.normals, 3)
                .unbind();
    }
}
