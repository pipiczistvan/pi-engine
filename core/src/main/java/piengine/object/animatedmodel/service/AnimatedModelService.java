package piengine.object.animatedmodel.service;

import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.vertexarray.VertexArray;
import piengine.io.loader.dae.domain.ColladaDto;
import piengine.io.loader.dae.domain.GeometryData;
import piengine.io.loader.dae.domain.Joint;
import piengine.io.loader.dae.domain.JointData;
import piengine.io.loader.dae.loader.ColladaLoader;
import piengine.object.animatedmodel.domain.AnimatedModel;
import piengine.object.animatedmodel.domain.AnimatedModelKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

import static piengine.io.interpreter.vertexarray.VertexAttribute.JOINT_INDEX;
import static piengine.io.interpreter.vertexarray.VertexAttribute.NORMAL;
import static piengine.io.interpreter.vertexarray.VertexAttribute.TEXTURE_COORD;
import static piengine.io.interpreter.vertexarray.VertexAttribute.VERTEX;
import static piengine.io.interpreter.vertexarray.VertexAttribute.WEIGHT;

@Component
public class AnimatedModelService extends SupplierService<AnimatedModelKey, AnimatedModel> {

    private final ColladaLoader colladaLoader;
    private final Map<String, VertexArray> vertexArrayMap;

    @Wire
    public AnimatedModelService(final ColladaLoader colladaLoader) {
        this.colladaLoader = colladaLoader;
        this.vertexArrayMap = new HashMap<>();
    }

    @Override
    public AnimatedModel supply(final AnimatedModelKey key) {
        ColladaDto collada = colladaLoader.load(key.colladaFile);
        VertexArray vertexArray = vertexArrayMap.computeIfAbsent(key.colladaFile, k -> createVertexArray(collada.geometryData));
        Joint rootJoint = createJointHierarchy(collada.skeletonData.headJoint);

        return new AnimatedModel(key.parent, vertexArray, key.texture, rootJoint, collada.skeletonData.jointCount);
    }

    @Override
    public void terminate() {
        vertexArrayMap.values().forEach(VertexArray::clear);
    }

    private VertexArray createVertexArray(final GeometryData geometry) {
        return new VertexArray(geometry.indices.length)
                .bind()
                .attachIndexBuffer(geometry.indices)
                .attachVertexBuffer(VERTEX, geometry.vertices, 3)
                .attachVertexBuffer(TEXTURE_COORD, geometry.textureCoords, 2)
                .attachVertexBuffer(NORMAL, geometry.normals, 3)
                .attachVertexBuffer(JOINT_INDEX, geometry.jointIds, 3)
                .attachVertexBuffer(WEIGHT, geometry.vertexWeights, 3)
                .unbind();
    }

    private Joint createJointHierarchy(final JointData jointData) {
        Joint joint = new Joint(jointData.index, jointData.nameId, jointData.bindLocalTransform);
        for (JointData child : jointData.children) {
            joint.addChild(createJointHierarchy(child));
        }
        return joint;
    }
}
