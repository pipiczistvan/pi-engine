package piengine.object.terrain.accessor;

public class TerrainIndexGenerator {

    public int[] generateIndexBuffer(final int terrainWidth, final int terrainHeight) {
        int[] indices = new int[(terrainHeight - 1) * (terrainWidth - 1) * 6];
        int rowLength = (terrainWidth - 1) * 2;
        int pointer = storeTopSection(indices, rowLength, terrainWidth, terrainHeight);
        pointer = storeSecondLastLine(indices, rowLength, terrainWidth, terrainHeight, pointer);
        storeLastLine(indices, rowLength, terrainWidth, terrainHeight, pointer);
        return indices;
    }

    private int storeTopSection(final int[] indices, final int rowLength, final int terrainWidth, final int terrainHeight) {
        int pointer = 0;
        for (int row = 0; row < terrainHeight - 3; row++) {
            for (int col = 0; col < terrainWidth - 1; col++) {
                int topLeft = (row * rowLength) + (col * 2);
                int topRight = topLeft + 1;
                int bottomLeft = topLeft + rowLength;
                int bottomRight = bottomLeft + 1;
                pointer = storeQuad(topLeft, topRight, bottomLeft, bottomRight, indices, col % 2 != row % 2, pointer);
            }
        }
        return pointer;
    }

    private int storeSecondLastLine(final int[] indices, final int rowLength, final int terrainWidth, final int terrainHeight, int pointer) {
        int row = terrainHeight - 3;
        for (int col = 0; col < terrainWidth - 1; col++) {
            int topLeft = (row * rowLength) + (col * 2);
            int topRight = topLeft + 1;
            int bottomLeft = (topLeft + rowLength) - col;
            int bottomRight = bottomLeft + 1;
            pointer = storeQuad(topLeft, topRight, bottomLeft, bottomRight, indices, col % 2 != row % 2, pointer);
        }
        return pointer;
    }

    private int storeLastLine(final int[] indices, final int rowLength, final int terrainWidth, final int terrainHeight, int pointer) {
        int row = terrainHeight - 2;
        for (int col = 0; col < terrainWidth - 1; col++) {
            int topLeft = (row * rowLength) + col;
            int topRight = topLeft + 1;
            int bottomLeft = (topLeft + terrainWidth);
            int bottomRight = bottomLeft + 1;
            pointer = storeLastRowQuad(topLeft, topRight, bottomLeft, bottomRight, indices, col % 2 != row % 2, pointer);
        }
        return pointer;
    }

    private int storeQuad(final int topLeft, final int topRight, final int bottomLeft,
                          final int bottomRight, final int[] indices, final boolean rightHanded,
                          int pointer) {
        pointer = storeLeftTriangle(topLeft, topRight, bottomLeft, bottomRight, indices, rightHanded, pointer);
        indices[pointer++] = topRight;
        indices[pointer++] = rightHanded ? topLeft : bottomLeft;
        indices[pointer++] = bottomRight;
        return pointer;
    }

    private int storeLastRowQuad(final int topLeft, final int topRight, final int bottomLeft,
                                 final int bottomRight, final int[] indices,final boolean rightHanded,
                                 int pointer) {
        pointer = storeLeftTriangle(topLeft, topRight, bottomLeft, bottomRight, indices, rightHanded, pointer);
        indices[pointer++] = bottomRight;
        indices[pointer++] = topRight;
        indices[pointer++] = rightHanded ? topLeft : bottomLeft;
        return pointer;
    }

    private int storeLeftTriangle(final int topLeft, final int topRight, final int bottomLeft,
                                  final int bottomRight, final int[] indices, final boolean rightHanded,
                                  int pointer) {
        indices[pointer++] = topLeft;
        indices[pointer++] = bottomLeft;
        indices[pointer++] = rightHanded ? bottomRight : topRight;
        return pointer;
    }
}
