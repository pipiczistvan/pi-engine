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
        Vector4f indicator = new Vector4f(0, 0, 0, 0);

        int index0 = left ? 0 : 2;
        positions.add(cornerPos[index0]);
        indicators.add(indicator);

        int index1 = 1;
        positions.add(cornerPos[index1]);
        indicators.add(indicator);

        int index2 = left ? 2 : 3;
        positions.add(cornerPos[index2]);
        indicators.add(indicator);
    }

    private static Vector2f[] calculateCornerPositions(int col, int row, float width, float height) {
        Vector2f[] vertices = new Vector2f[4];
        vertices[0] = new Vector2f(col / width, row / height);
        vertices[1] = new Vector2f(col / width, (row + 1) / height);
        vertices[2] = new Vector2f((col + 1) / width, row / height);
        vertices[3] = new Vector2f((col + 1) / width, (row + 1) / height);
        return vertices;
    }

}
