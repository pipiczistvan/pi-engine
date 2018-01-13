package piengine.object.water.accessor;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import piengine.core.utils.MatrixUtils;
import piengine.core.utils.VectorUtils;
import piengine.object.water.domain.WaterGrid;

import java.util.ArrayList;
import java.util.List;

public class WaterGridGenerator {

    public WaterGrid generate(final Vector2i size, final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        Matrix4f transformation = MatrixUtils.MODEL_MATRIX(position, rotation, scale);

        List<Vector3f> positionList = new ArrayList<>();
        List<Vector4f> indicatorList = new ArrayList<>();

        for (int row = 0; row < size.y; row++) {
            for (int col = 0; col < size.x; col++) {
                Vector3f[] cornerPos = calculateCornerPositions(transformation, col, row, size.x, size.y);
                storeTriangle(cornerPos, positionList, indicatorList, true);
                storeTriangle(cornerPos, positionList, indicatorList, false);
            }
        }

        float[] positions = VectorUtils.vector3fToFloatArray(positionList);
        float[] indicators = VectorUtils.vector4fToFloatArray(indicatorList);

        return new WaterGrid(positions, indicators);
    }

    private static void storeTriangle(Vector3f[] cornerPos, final List<Vector3f> positions, final List<Vector4f> indicators, boolean left) {
        int index0 = left ? 0 : 2;
        int index1 = 1;
        int index2 = left ? 2 : 3;

        positions.add(cornerPos[index0]);
        indicators.add(getIndicator(index0, cornerPos, index1, index2));

        positions.add(cornerPos[index1]);
        indicators.add(getIndicator(index1, cornerPos, index2, index0));

        positions.add(cornerPos[index2]);
        indicators.add(getIndicator(index2, cornerPos, index0, index1));
    }

    private static Vector3f[] calculateCornerPositions(final Matrix4f transformation, final int col, final int row, final float width, final float height) {
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(col / width, 0, row / height);
        vertices[1] = new Vector3f(col / width, 0, (row + 1) / height);
        vertices[2] = new Vector3f((col + 1) / width, 0, row / height);
        vertices[3] = new Vector3f((col + 1) / width, 0, (row + 1) / height);

        for (Vector3f vertex : vertices) {
            transformation.transformPosition(vertex);
        }

        return vertices;
    }

    private static Vector4f getIndicator(int currentVertex, Vector3f[] vertexPositions, int vertex1, int vertex2) {
        Vector3f currentVertexPos = vertexPositions[currentVertex];
        Vector3f vertex1Pos = vertexPositions[vertex1];
        Vector3f vertex2Pos = vertexPositions[vertex2];

        Vector3f offset1 = new Vector3f();
        vertex1Pos.sub(currentVertexPos, offset1);

        Vector3f offset2 = new Vector3f();
        vertex2Pos.sub(currentVertexPos, offset2);

        return new Vector4f(offset1.x, offset1.z, offset2.x, offset2.z);
    }
}
