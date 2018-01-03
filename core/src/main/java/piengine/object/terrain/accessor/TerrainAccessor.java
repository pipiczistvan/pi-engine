package piengine.object.terrain.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.object.terrain.domain.TerrainData;
import puppeteer.annotation.premade.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.IMAGES_LOCATION;

@Component
public class TerrainAccessor implements Accessor<TerrainData> {

    private static final String ROOT = get(IMAGES_LOCATION);
    private static final String PNG_EXT = "png";
    //todo: nem dinamikus
    private static final float TILE_SIZE = 0.5f;
    private static final float MAX_HEIGHT = 10;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

    private final ResourceLoader loader;

    public TerrainAccessor() {
        this.loader = new ResourceLoader(ROOT, PNG_EXT);
    }

    @Override
    public TerrainData access(final String file) {
        BufferedImage heightmap;
        try {
            heightmap = loader.loadBufferedImage(file);
        } catch (IOException e) {
            throw new PIEngineException("Could not load heightmap %s!", file, e);
        }

        int width = heightmap.getWidth();
        int height = heightmap.getHeight();
        int count = width * height;

        float[] vertices = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (width - 1) * (height - 1)];
        float[][] heights = new float[height][width];

        int vertexPointer = 0;
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {
                vertices[vertexPointer * 3] = x * TILE_SIZE;
                heights[z][x] = getHeight(x, z, heightmap);
                vertices[vertexPointer * 3 + 1] = heights[z][x];
                vertices[vertexPointer * 3 + 2] = z * TILE_SIZE;
                //todo: nem kell
                textureCoords[vertexPointer * 2] = (float) x / ((float) width - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) z / ((float) height - 1);

                vertexPointer++;
            }
        }

        int indexPointer = 0;
        for (int z = 0; z < height - 1; z++) {
            for (int x = 0; x < width - 1; x++) {
                int topLeft = (z * width) + x;
                int topRight = topLeft + 1;
                int bottomLeft = ((z + 1) * width) + x;
                int bottomRight = bottomLeft + 1;

                indices[indexPointer++] = topLeft;
                indices[indexPointer++] = bottomLeft;
                indices[indexPointer++] = topRight;
                indices[indexPointer++] = topRight;
                indices[indexPointer++] = bottomLeft;
                indices[indexPointer++] = bottomRight;
            }
        }

        return new TerrainData(vertices, indices, textureCoords, heights, TILE_SIZE);
    }

    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getWidth() || z < 0 || z >= image.getHeight()) {
            return 0;
        }

        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOR / 2f;
        height /= MAX_PIXEL_COLOR / 2f;
        height *= MAX_HEIGHT;

        return height;
    }
}
