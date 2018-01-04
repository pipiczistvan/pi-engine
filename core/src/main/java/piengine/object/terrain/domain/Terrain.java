package piengine.object.terrain.domain;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.object.entity.domain.Entity;
import piengine.object.entity.domain.EntityDomain;

public class Terrain extends EntityDomain<TerrainDao> {

    private final float[][] heights;
    private final float size;

    public Terrain(final Entity parent, final TerrainDao dao, final float[][] heights, final float size) {
        super(parent, dao);

        this.heights = heights;
        this.size = size;
    }

    public float getHeight(final float x, final float z) {
        Vector3f position = getPosition();

        float terrainX = x - position.x;
        float terrainZ = z - position.z;

        int gridX = (int) Math.floor(terrainX / size);
        int gridZ = (int) Math.floor(terrainZ / size);

        if (gridX >= heights[0].length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        }

        float xCoord = (terrainX % size);
        float zCoord = (terrainZ % size);

        if (xCoord <= (1 - zCoord)) {
            return barryCentric(
                    new Vector3f(0, heights[gridZ][gridX], 0),
                    new Vector3f(1, heights[gridZ][gridX + 1], 0),
                    new Vector3f(0, heights[gridZ + 1][gridX], 1),
                    new Vector2f(xCoord, zCoord));
        } else {
            return barryCentric(
                    new Vector3f(1, heights[gridZ][gridX + 1], 0),
                    new Vector3f(1, heights[gridZ + 1][gridX + 1], 1),
                    new Vector3f(0, heights[gridZ + 1][gridX], 1),
                    new Vector2f(xCoord, zCoord));
        }
    }

    private static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }
}
