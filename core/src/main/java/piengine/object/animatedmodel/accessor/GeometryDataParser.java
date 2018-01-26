package piengine.object.animatedmodel.accessor;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import piengine.core.xml.collada.domain.common.Input;
import piengine.core.xml.collada.domain.common.Source;
import piengine.core.xml.collada.domain.geometry.Geometry;
import piengine.core.xml.collada.domain.geometry.Mesh;
import piengine.core.xml.collada.domain.geometry.Polylist;
import piengine.object.animatedmodel.domain.GeometryData;
import piengine.object.animatedmodel.domain.Vertex;
import piengine.object.animatedmodel.domain.VertexSkinData;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

import static piengine.core.xml.collada.domain.common.Input.findInputBySemantic;
import static piengine.core.xml.collada.domain.common.Source.findSourceById;

@Component
public class GeometryDataParser {

    private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    public GeometryData parse(final Geometry geometry, final List<VertexSkinData> vertexWeights) {
        List<Vertex> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        readRawData(geometry.mesh, vertices, normals, textures, vertexWeights);
        assembleVertices(geometry.mesh.polylist, vertices, indices);
        removeUnusedVertices(vertices);

        float[] verticesArray = new float[vertices.size() * 3];
        float[] texturesArray = new float[vertices.size() * 2];
        float[] normalsArray = new float[vertices.size() * 3];
        int[] jointIdsArray = new int[vertices.size() * 3];
        float[] weightsArray = new float[vertices.size() * 3];

        convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray, normalsArray, jointIdsArray, weightsArray);
        int[] indicesArray = convertIndicesListToArray(indices);
        return new GeometryData(verticesArray, texturesArray, normalsArray, indicesArray, jointIdsArray, weightsArray);
    }

    private void readRawData(final Mesh mesh, final List<Vertex> vertices, final List<Vector3f> normals, final List<Vector2f> textures, final List<VertexSkinData> vertexWeights) {
        readPositions(mesh, vertices, vertexWeights);
        readNormals(mesh, normals);
        readTextureCoords(mesh, textures);
    }

    private void readPositions(final Mesh mesh, final List<Vertex> vertices, final List<VertexSkinData> vertexWeights) {
        String positionsId = mesh.vertices.input.source.substring(1);
        Source source = findSourceById(mesh.source, positionsId);

        for (int i = 0; i < source.float_array.length / 3; i++) {
            float x = source.float_array[i * 3];
            float y = source.float_array[i * 3 + 1];
            float z = source.float_array[i * 3 + 2];
            Vector4f position = new Vector4f(x, y, z, 1);
            CORRECTION.transform(position, position);
            vertices.add(new Vertex(vertices.size(), new Vector3f(position.x, position.y, position.z), vertexWeights.get(vertices.size())));
        }
    }

    private void readNormals(final Mesh mesh, final List<Vector3f> normals) {
        Input input = findInputBySemantic(mesh.polylist.input, "NORMAL");
        Source source = findSourceById(mesh.source, input.source.substring(1));

        for (int i = 0; i < source.float_array.length / 3; i++) {
            float x = source.float_array[i * 3];
            float y = source.float_array[i * 3 + 1];
            float z = source.float_array[i * 3 + 2];
            Vector4f norm = new Vector4f(x, y, z, 0f);
            CORRECTION.transform(norm, norm);
            normals.add(new Vector3f(norm.x, norm.y, norm.z));
        }
    }

    private void readTextureCoords(final Mesh mesh, final List<Vector2f> textures) {
        Input input = findInputBySemantic(mesh.polylist.input, "TEXCOORD");
        Source source = findSourceById(mesh.source, input.source.substring(1));

        for (int i = 0; i < source.float_array.length / 2; i++) {
            float s = source.float_array[i * 2];
            float t = source.float_array[i * 2 + 1];
            textures.add(new Vector2f(s, t));
        }
    }

    private void assembleVertices(final Polylist polylist, final List<Vertex> vertices, final List<Integer> indices) {
        int typeCount = polylist.input.length;
        for (int i = 0; i < polylist.p.length / typeCount; i++) {
            int positionIndex = polylist.p[i * typeCount];
            int normalIndex = polylist.p[i * typeCount + 1];
            int texCoordIndex = polylist.p[i * typeCount + 2];
            processVertex(vertices, indices, positionIndex, normalIndex, texCoordIndex);
        }
    }

    private Vertex processVertex(final List<Vertex> vertices, final List<Integer> indices, int posIndex, int normIndex, int texIndex) {
        Vertex currentVertex = vertices.get(posIndex);
        if (!currentVertex.isSet()) {
            currentVertex.setTextureIndex(texIndex);
            currentVertex.setNormalIndex(normIndex);
            indices.add(posIndex);
            return currentVertex;
        } else {
            return dealWithAlreadyProcessedVertex(vertices, indices, currentVertex, texIndex, normIndex);
        }
    }

    private int[] convertIndicesListToArray(final List<Integer> indices) {
        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indicesArray.length; i++) {
            indicesArray[i] = indices.get(i);
        }
        return indicesArray;
    }

    private float convertDataToArrays(final List<Vertex> vertices, final List<Vector2f> textures, final List<Vector3f> normals,
                                      final float[] verticesArray, final float[] texturesArray, final float[] normalsArray,
                                      final int[] jointIdsArray, final float[] weightsArray) {
        float furthestPoint = 0;
        for (int i = 0; i < vertices.size(); i++) {
            Vertex currentVertex = vertices.get(i);
            if (currentVertex.getLength() > furthestPoint) {
                furthestPoint = currentVertex.getLength();
            }
            Vector3f position = currentVertex.getPosition();
            Vector2f textureCoord = textures.get(currentVertex.getTextureIndex());
            Vector3f normalVector = normals.get(currentVertex.getNormalIndex());
            verticesArray[i * 3] = position.x;
            verticesArray[i * 3 + 1] = position.y;
            verticesArray[i * 3 + 2] = position.z;
            texturesArray[i * 2] = textureCoord.x;
            texturesArray[i * 2 + 1] = 1 - textureCoord.y;
            normalsArray[i * 3] = normalVector.x;
            normalsArray[i * 3 + 1] = normalVector.y;
            normalsArray[i * 3 + 2] = normalVector.z;
            VertexSkinData weights = currentVertex.getWeightsData();
            jointIdsArray[i * 3] = weights.jointIds.get(0);
            jointIdsArray[i * 3 + 1] = weights.jointIds.get(1);
            jointIdsArray[i * 3 + 2] = weights.jointIds.get(2);
            weightsArray[i * 3] = weights.weights.get(0);
            weightsArray[i * 3 + 1] = weights.weights.get(1);
            weightsArray[i * 3 + 2] = weights.weights.get(2);

        }
        return furthestPoint;
    }

    private Vertex dealWithAlreadyProcessedVertex(final List<Vertex> vertices, final List<Integer> indices,
                                                  final Vertex previousVertex, final int newTextureIndex, final int newNormalIndex) {
        if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
            indices.add(previousVertex.getIndex());
            return previousVertex;
        } else {
            Vertex anotherVertex = previousVertex.getDuplicateVertex();
            if (anotherVertex != null) {
                return dealWithAlreadyProcessedVertex(vertices, indices, anotherVertex, newTextureIndex, newNormalIndex);
            } else {
                Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition(), previousVertex.getWeightsData());
                duplicateVertex.setTextureIndex(newTextureIndex);
                duplicateVertex.setNormalIndex(newNormalIndex);
                previousVertex.setDuplicateVertex(duplicateVertex);
                vertices.add(duplicateVertex);
                indices.add(duplicateVertex.getIndex());
                return duplicateVertex;
            }
        }
    }

    private void removeUnusedVertices(final List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            vertex.averageTangents();
            if (!vertex.isSet()) {
                vertex.setTextureIndex(0);
                vertex.setNormalIndex(0);
            }
        }
    }
}
