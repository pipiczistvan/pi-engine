package piengine.object.terrain.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.core.utils.MathUtils;
import piengine.object.terrain.domain.TerrainData;
import piengine.object.terrain.domain.TerrainKey;
import puppeteer.annotation.premade.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

@Component
public class TerrainAccessor implements Accessor<TerrainKey, TerrainData> {

    private static final String ROOT = get(IMAGES_LOCATION);
    private static final String PNG_EXT = "png";
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
    private static final Color[] BIOME_COLORS = {
            new Color(0.78823529412f, 0.69803921569f, 0.38823529412f),
            new Color(0.52941176471f, 0.72156862745f, 0.32156862745f),
            new Color(0.3137254902f, 0.67058823529f, 0.36470588235f),
            new Color(0.47058823529f, 0.47058823529f, 0.47058823529f),
            new Color(0.78431372549f, 0.78431372549f, 0.82352941176f)
    };
    private static final float COLOR_SPREAD = 0.45f;
    private static final float COLOR_PART = 1f / (BIOME_COLORS.length - 1);

    private final ResourceLoader loader;

    public TerrainAccessor() {
        this.loader = new ResourceLoader(ROOT, PNG_EXT);
    }

    @Override
    public TerrainData access(final TerrainKey key) {
        BufferedImage heightmap;
        try {
            heightmap = loader.loadBufferedImage(key.heightmap);
        } catch (IOException e) {
            throw new PIEngineException("Could not load heightmap %s!", key.heightmap, e);
        }

        int width = heightmap.getWidth();
        int height = heightmap.getHeight();
        int count = width * height;
        float tileSizeX = 1f / width;
        float tileSizeZ = 1f / height;

        float[] vertices = new float[count * 3];
        int[] indices = new int[6 * (width - 1) * (height - 1)];
        float[] colors = new float[count * 3];
        float[][] heights = new float[height][width];

        int vertexPointer = 0;
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {
                heights[z][x] = getHeight(x, z, heightmap);

                vertices[vertexPointer * 3] = x * tileSizeX;
                vertices[vertexPointer * 3 + 1] = heights[z][x];
                vertices[vertexPointer * 3 + 2] = z * tileSizeZ;

                Color color = getColor(heights[z][x]);
                colors[vertexPointer * 3] = color.r;
                colors[vertexPointer * 3 + 1] = color.g;
                colors[vertexPointer * 3 + 2] = color.b;

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

        return new TerrainData(key.parent, vertices, indices, colors, heights);
    }

    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getWidth() || z < 0 || z >= image.getHeight()) {
            return 0;
        }

        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOR / 2f;
        height /= MAX_PIXEL_COLOR / 2f;

        return height;
    }

    private Color getColor(float height) {
        float value = (height + 1) / 2f;
        value = MathUtils.clamp((value - COLOR_SPREAD / 2) * (1f / COLOR_SPREAD), 0f, 0.9999f);
        int firstBiome = (int) Math.floor(value / COLOR_PART);
        float blend = (value - (firstBiome * COLOR_PART)) / COLOR_PART;

        return ColorUtils.interpolateColors(BIOME_COLORS[firstBiome], BIOME_COLORS[firstBiome + 1], blend);
    }
}
