package piengine.object.mesh.accessor;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.object.mesh.domain.ParsedMeshData;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

@Component
public class ObjParser {

    private static final String VERTEX_PREFIX = "v";
    private static final String TEXTURE_PREFIX = "vt";
    private static final String NORMAL_PREFIX = "vn";
    private static final String FACE_PREFIX = "f";

    public ParsedMeshData parseSource(final String[] source) {
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] verticesArray;
        float[] texturesArray;
        float[] normalsArray;
        int[] indicesArray;

        int lineIndex = 0;

        lineReader:
        while (lineIndex < source.length) {
            String[] splatLine = source[lineIndex].split(" ");
            switch (splatLine[0]) {
                case VERTEX_PREFIX:
                    vertices.add(parseVector3f(splatLine));
                    break;
                case TEXTURE_PREFIX:
                    textures.add(parseVector2f(splatLine));
                    break;
                case NORMAL_PREFIX:
                    normals.add(parseVector3f(splatLine));
                    break;
                case FACE_PREFIX:
                    break lineReader;
            }

            lineIndex++;
        }

        texturesArray = new float[vertices.size() * 2];
        normalsArray = new float[vertices.size() * 3];

        while (lineIndex < source.length) {
            String[] splatLine = source[lineIndex].split(" ");

            if (FACE_PREFIX.equals(splatLine[0])) {
                // processing faces
                String[] vertex1 = splatLine[1].split("/");
                String[] vertex2 = splatLine[2].split("/");
                String[] vertex3 = splatLine[3].split("/");

                processVertex(vertex1, indices, textures, texturesArray, normals, normalsArray);
                processVertex(vertex2, indices, textures, texturesArray, normals, normalsArray);
                processVertex(vertex3, indices, textures, texturesArray, normals, normalsArray);
            }

            lineIndex++;
        }

        verticesArray = new float[vertices.size() * 3];
        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        indicesArray = new int[indices.size()];
        int indexPointer = 0;
        for (Integer index : indices) {
            indicesArray[indexPointer++] = index;
        }

        return new ParsedMeshData(verticesArray, indicesArray, texturesArray, normalsArray);
    }

    private void processVertex(final String[] vertexData, final List<Integer> indices,
                               final List<Vector2f> textures, final float[] texturesArray,
                               final List<Vector3f> normals, final float[] normalsArray) {
        int currentVertexPointer = parseInt(vertexData[0]) - 1;

        indices.add(currentVertexPointer);

        if (!textures.isEmpty()) {
            Vector2f currentTexture = textures.get(parseInt(vertexData[1]) - 1);
            texturesArray[currentVertexPointer * 2] = currentTexture.x;
            texturesArray[currentVertexPointer * 2 + 1] = 1 - currentTexture.y;
        }

        Vector3f currentNormal = normals.get(parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.z;
    }

    private Vector2f parseVector2f(final String[] splatString) {
        return new Vector2f(parseFloat(splatString[1]), parseFloat(splatString[2]));
    }

    private Vector3f parseVector3f(final String[] splatString) {
        return new Vector3f(parseFloat(splatString[1]), parseFloat(splatString[2]), parseFloat(splatString[3]));
    }
}
