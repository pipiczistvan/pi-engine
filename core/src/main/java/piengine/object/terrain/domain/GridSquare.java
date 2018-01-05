package piengine.object.terrain.domain;

import org.joml.Vector3f;
import piengine.core.base.type.color.Color;
import piengine.core.utils.MathUtils;

import java.util.List;

public class GridSquare {

    private final int row;
    private final int col;
    private final int lastRowIndex;
    private final int lastColIndex;
    private final Vector3f[] positions;
    private final Color[] colors;
    private final Vector3f normalLeft;
    private final Vector3f normalRight;

    public GridSquare(final int row, final int col, final float[][] heights, final Color[][] colors) {
        this.positions = calculateCornerPositions(col, row, heights);
        this.colors = calculateCornerColours(col, row, colors);
        this.lastRowIndex = heights.length - 2;
        this.lastColIndex = heights[0].length - 2;
        this.row = row;
        this.col = col;
        boolean rightHanded = col % 2 != row % 2;
        this.normalLeft = MathUtils.calculateNormal(positions[0], positions[1], positions[rightHanded ? 3 : 2]);
        this.normalRight = MathUtils.calculateNormal(positions[2], positions[rightHanded ? 0 : 1], positions[3]);
    }

    public void storeSquareData(final List<Vector3f> positions, final List<Color> colors, final List<Vector3f> normals) {
        storeTopLeftVertex(positions, colors, normals);
        if (row != lastRowIndex || col == lastColIndex) {
            storeTopRightVertex(positions, colors, normals);
        }
    }

    public void storeBottomRowData(final List<Vector3f> positions, final List<Color> colors, final List<Vector3f> normals) {
        if (col == 0) {
            storeBottomLeftVertex(positions, colors, normals);
        }
        storeBottomRightVertex(positions, colors, normals);
    }

    private Color[] calculateCornerColours(final int col, final int row, final Color[][] colors) {
        Color[] cornerCols = new Color[4];
        cornerCols[0] = colors[row][col];
        cornerCols[1] = colors[row + 1][col];
        cornerCols[2] = colors[row][col + 1];
        cornerCols[3] = colors[row + 1][col + 1];
        return cornerCols;
    }

    private Vector3f[] calculateCornerPositions(final int col, final int row, final float[][] heights) {
        Vector3f[] vertices = new Vector3f[4];
        float width = (float) heights[0].length;
        float height = (float) heights.length;

        vertices[0] = new Vector3f(col / width, heights[row][col], row / height);
        vertices[1] = new Vector3f(col / width, heights[row + 1][col], (row + 1) / height);
        vertices[2] = new Vector3f((col + 1) / width, heights[row][col + 1], row / height);
        vertices[3] = new Vector3f((col + 1) / width, heights[row + 1][col + 1], (row + 1) / height);
        return vertices;
    }

    private void storeTopLeftVertex(final List<Vector3f> positions, final List<Color> colors, final List<Vector3f> normals) {
        positions.add(this.positions[0]);
        colors.add(this.colors[0]);
        normals.add(this.normalLeft);
    }

    private void storeTopRightVertex(final List<Vector3f> positions, final List<Color> colors, final List<Vector3f> normals) {
        positions.add(this.positions[2]);
        colors.add(this.colors[2]);
        normals.add(this.normalRight);
    }

    private void storeBottomLeftVertex(final List<Vector3f> positions, final List<Color> colors, final List<Vector3f> normals) {
        positions.add(this.positions[1]);
        colors.add(this.colors[1]);
        normals.add(this.normalLeft);
    }

    private void storeBottomRightVertex(final List<Vector3f> positions, final List<Color> colors, final List<Vector3f> normals) {
        positions.add(this.positions[3]);
        colors.add(this.colors[3]);
        normals.add(this.normalRight);
    }
}
