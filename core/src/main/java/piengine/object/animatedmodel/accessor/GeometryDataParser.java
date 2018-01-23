package piengine.object.animatedmodel.accessor;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import piengine.core.xml.domain.XmlNode;
import piengine.object.animatedmodel.domain.GeometryData;
import piengine.object.animatedmodel.domain.Vertex;
import piengine.object.animatedmodel.domain.VertexSkinData;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GeometryDataParser {

    private static final Matrix4f CORRECTION = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(1, 0, 0));

    public GeometryData parseXmlNode(final XmlNode geometryNode, final List<VertexSkinData> vertexWeights) {
        List<Vertex> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        XmlNode meshNode = geometryNode.getChild("geometry").getChild("mesh");

        readRawData(meshNode, vertices, normals, textures, vertexWeights);
        assembleVertices(meshNode, vertices, indices);
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

    private void readRawData(final XmlNode geometryNode, final List<Vertex> vertices, final List<Vector3f> normals, final List<Vector2f> textures, final List<VertexSkinData> vertexWeights) {
        readPositions(geometryNode, vertices, vertexWeights);
        readNormals(geometryNode, normals);
        readTextureCoords(geometryNode, textures);
    }

    private void readPositions(final XmlNode geometryNode, final List<Vertex> vertices, final List<VertexSkinData> vertexWeights) {
        String positionsId = geometryNode.getChild("vertices").getChild("input").getAttribute("source").substring(1);
        XmlNode positionsData = geometryNode.getChildWithAttribute("source", "id", positionsId).getChild("float_array");
        int count = Integer.parseInt(positionsData.getAttribute("count"));
        String[] posData = positionsData.getData().split(" ");
        for (int i = 0; i < count / 3; i++) {
            float x = Float.parseFloat(posData[i * 3]);
            float y = Float.parseFloat(posData[i * 3 + 1]);
            float z = Float.parseFloat(posData[i * 3 + 2]);
            Vector4f position = new Vector4f(x, y, z, 1);
            CORRECTION.transform(position, position);
            vertices.add(new Vertex(vertices.size(), new Vector3f(position.x, position.y, position.z), vertexWeights.get(vertices.size())));
        }
    }

    private void readNormals(final XmlNode geometryNode, final List<Vector3f> normals) {
        String normalsId = geometryNode.getChild("polylist").getChildWithAttribute("input", "semantic", "NORMAL")
                .getAttribute("source").substring(1);
        XmlNode normalsData = geometryNode.getChildWithAttribute("source", "id", normalsId).getChild("float_array");
        int count = Integer.parseInt(normalsData.getAttribute("count"));
        String[] normData = normalsData.getData().split(" ");
        for (int i = 0; i < count / 3; i++) {
            float x = Float.parseFloat(normData[i * 3]);
            float y = Float.parseFloat(normData[i * 3 + 1]);
            float z = Float.parseFloat(normData[i * 3 + 2]);
            Vector4f norm = new Vector4f(x, y, z, 0f);
            CORRECTION.transform(norm, norm);
            normals.add(new Vector3f(norm.x, norm.y, norm.z));
        }
    }

    private void readTextureCoords(final XmlNode geometryNode, final List<Vector2f> textures) {
        String texCoordsId = geometryNode.getChild("polylist").getChildWithAttribute("input", "semantic", "TEXCOORD")
                .getAttribute("source").substring(1);
        XmlNode texCoordsData = geometryNode.getChildWithAttribute("source", "id", texCoordsId).getChild("float_array");
        int count = Integer.parseInt(texCoordsData.getAttribute("count"));
        String[] texData = texCoordsData.getData().split(" ");
        for (int i = 0; i < count / 2; i++) {
            float s = Float.parseFloat(texData[i * 2]);
            float t = Float.parseFloat(texData[i * 2 + 1]);
            textures.add(new Vector2f(s, t));
        }
    }

    private void assembleVertices(final XmlNode geometryNode, final List<Vertex> vertices, final List<Integer> indices) {
        XmlNode poly = geometryNode.getChild("polylist");
        int typeCount = poly.getChildren("input").size();
        String[] indexData = poly.getChild("p").getData().split(" ");
        for (int i = 0; i < indexData.length / typeCount; i++) {
            int positionIndex = Integer.parseInt(indexData[i * typeCount]);
            int normalIndex = Integer.parseInt(indexData[i * typeCount + 1]);
            int texCoordIndex = Integer.parseInt(indexData[i * typeCount + 2]);
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
