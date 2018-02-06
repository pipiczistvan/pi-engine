package piengine.io.loader.terrain.loader;

import piengine.core.base.exception.PIEngineException;
import piengine.core.base.type.color.Color;
import piengine.io.loader.AbstractLoader;
import piengine.io.loader.ResourceLoader;
import piengine.io.loader.terrain.domain.TerrainDto;
import piengine.io.loader.terrain.domain.TerrainGrid;
import piengine.io.loader.terrain.generator.TerrainColorGenerator;
import piengine.io.loader.terrain.generator.TerrainGridGenerator;
import piengine.object.terrain.domain.TerrainKey;
import puppeteer.annotation.premade.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

@Component
public class TerrainLoader extends AbstractLoader<TerrainKey, TerrainDto> {

    private static final String ROOT = get(IMAGES_LOCATION);
    private static final String PNG_EXT = "png";
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
    private static final float COLOR_SPREAD = 0.45f;

    private final ResourceLoader loader;
    private final TerrainGridGenerator gridGenerator;

    public TerrainLoader() {
        this.loader = new ResourceLoader(ROOT, PNG_EXT);
        this.gridGenerator = new TerrainGridGenerator();
    }

    @Override
    protected TerrainDto createDto(final TerrainKey key) {
        TerrainColorGenerator colorGenerator = new TerrainColorGenerator(key.biomColors, COLOR_SPREAD);

        BufferedImage heightmap;
        try {
            heightmap = loader.loadBufferedImage(key.heightmap);
        } catch (IOException e) {
            throw new PIEngineException("Could not load heightmap %s!", key.heightmap, e);
        }

        int mapWidth = heightmap.getWidth();
        int mapHeight = heightmap.getHeight();

        float[][] heights = new float[mapHeight][mapWidth];
        Color[][] colors = new Color[mapHeight][mapWidth];

        for (int z = 0; z < mapHeight; z++) {
            for (int x = 0; x < mapWidth; x++) {
                float height = getHeight(x, z, heightmap);

                heights[z][x] = height;
                colors[z][x] = colorGenerator.generate(height);
            }
        }

        TerrainGrid grid = gridGenerator.generate(key.position, key.rotation, key.scale, heights, colors);

        return new TerrainDto(
                grid.positions,
                grid.indices,
                grid.colors,
                grid.normals,
                heights);
    }

    private float getHeight(final int x, final int z, final BufferedImage image) {
        if (x < 0 || x >= image.getWidth() || z < 0 || z >= image.getHeight()) {
            return 0;
        }

        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOR / 2f;
        height /= MAX_PIXEL_COLOR / 2f;

        return height;
    }
}
