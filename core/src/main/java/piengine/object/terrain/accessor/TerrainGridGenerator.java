package piengine.object.terrain.accessor;

import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.core.utils.VectorUtils;
import piengine.object.terrain.domain.GridSquare;
import piengine.object.terrain.domain.TerrainGrid;

import java.util.ArrayList;
import java.util.List;

public class TerrainGridGenerator {

    private final TerrainIndexGenerator indexGenerator;

    TerrainGridGenerator() {
        this.indexGenerator = new TerrainIndexGenerator();
    }

    public TerrainGrid generate(final float[][] heightMap, final Color[][] colorMap) {
        int heightMapWidth = heightMap[0].length;
        int heightMapHeight = heightMap.length;

        List<Vector3f> positionList = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();
        List<Vector3f> normalList = new ArrayList<>();

        GridSquare[] lastRow = new GridSquare[heightMapWidth - 1];
        for (int row = 0; row < heightMapHeight - 1; row++) {
            for (int col = 0; col < heightMapWidth - 1; col++) {
                GridSquare square = new GridSquare(row, col, heightMap, colorMap);
                square.storeSquareData(positionList, colorList, normalList);
                if (row == heightMapHeight - 2) {
                    lastRow[col] = square;
                }
            }
        }
        for (int i = 0; i < heightMapWidth - 1; i++) {
            lastRow[i].storeBottomRowData(positionList, colorList, normalList);
        }

        float[] positions = VectorUtils.convertListToArray(positionList);
        float[] colors = ColorUtils.convertListToArray(colorList);
        float[] normals = VectorUtils.convertListToArray(normalList);
        int[] indices = indexGenerator.generateIndexBuffer(heightMapWidth, heightMapHeight);

        return new TerrainGrid(positions, colors, normals, indices);
    }

}