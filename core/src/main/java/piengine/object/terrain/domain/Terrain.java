package piengine.object.terrain.domain;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.base.domain.Domain;

public class Terrain implements Domain<TerrainDao> {

    private final TerrainDao dao;
    private final float[][] heights;
    private final Vector3f position;
    private final Vector3f rotation;
    private final Vector3f scale;

    public Terrain(final TerrainDao dao, final float[][] heights, final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        this.dao = dao;
        this.heights = heights;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    private static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    @Override
    public TerrainDao getDao() {
        return dao;
    }

    public float getHeight(final float x, final float z) {
        float terrainX = x - position.x;
        float terrainZ = z - position.z;

        int terrainWidth = heights[0].length;
        int terrainLength = heights.length;

        float tileSizeX = scale.x / terrainWidth;
        float tileSizeZ = scale.z / terrainLength;

        int gridX = (int) Math.floor(terrainX / tileSizeX);
        int gridZ = (int) Math.floor(terrainZ / tileSizeZ);

        if (gridX >= terrainWidth - 1 || gridZ >= terrainLength - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        }

        float xCoord = (terrainX % tileSizeX);
        float zCoord = (terrainZ % tileSizeZ);

        float terrainHeight;
        if (xCoord <= (1 - zCoord)) {
            terrainHeight = barryCentric(
                    new Vector3f(0, heights[gridZ][gridX], 0),
                    new Vector3f(1, heights[gridZ][gridX + 1], 0),
                    new Vector3f(0, heights[gridZ + 1][gridX], 1),
                    new Vector2f(xCoord, zCoord));
        } else {
            terrainHeight = barryCentric(
                    new Vector3f(1, heights[gridZ][gridX + 1], 0),
                    new Vector3f(1, heights[gridZ + 1][gridX + 1], 1),
                    new Vector3f(0, heights[gridZ + 1][gridX], 1),
                    new Vector2f(xCoord, zCoord));
        }

        //todo: rotation nincs figyelembe véve
        return terrainHeight * scale.y + position.y;
    }
}
