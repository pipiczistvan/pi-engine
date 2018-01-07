package piengine.object.water.accessor;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import piengine.core.utils.VectorUtils;
import piengine.object.water.domain.WaterGrid;

import java.util.ArrayList;
import java.util.List;

public class WaterGridGenerator {

    public WaterGrid generate(final Vector2i size) {
        List<Vector2f> positionList = new ArrayList<>();
        List<Vector4f> indicatorList = new ArrayList<>();

        for (int row = 0; row < size.y; row++) {
            for (int col = 0; col < size.x; col++) {
                Vector2f[] cornerPos = calculateCornerPositions(col, row, size.x, size.y);
                storeTriangle(cornerPos, positionList, indicatorList, true);
                storeTriangle(cornerPos, positionList, indicatorList, false);
            }
        }

        float[] positions = VectorUtils.vector2fToFloatArray(positionList);
        float[] indicators = VectorUtils.vector4fToFloatArray(indicatorList);

        return new WaterGrid(positions, indicators);
    }

    private static void storeTriangle(Vector2f[] cornerPos, final List<Vector2f> positions, final List<Vector4f> indicators, boolean left) {
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

    private static Vector2f[] calculateCornerPositions(int col, int row, float width, float height) {
        Vector2f[] vertices = new Vector2f[4];
        vertices[0] = new Vector2f(col / width, row / height);
        vertices[1] = new Vector2f(col / width, (row + 1) / height);
        vertices[2] = new Vector2f((col + 1) / width, row / height);
        vertices[3] = new Vector2f((col + 1) / width, (row + 1) / height);
        return vertices;
    }

    private static Vector4f getIndicator(int currentVertex, Vector2f[] vertexPositions, int vertex1, int vertex2) {
        Vector2f currentVertexPos = vertexPositions[currentVertex];
        Vector2f vertex1Pos = vertexPositions[vertex1];
        Vector2f vertex2Pos = vertexPositions[vertex2];

        Vector2f offset1 = new Vector2f();
        vertex1Pos.sub(currentVertexPos, offset1);

        Vector2f offset2 = new Vector2f();
        vertex2Pos.sub(currentVertexPos, offset2);

        return new Vector4f(offset1.x, offset1.y, offset2.x, offset2.y);
    }
}
