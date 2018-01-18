package piengine.visual.cubemap.manager;

import org.joml.Vector2i;
import piengine.visual.cubemap.domain.CubeMap;
import piengine.visual.cubemap.domain.CubeMapKey;
import piengine.visual.cubemap.service.CubeMapService;
import piengine.visual.image.domain.ImageData;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.texture.domain.TextureData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.opengl.GL11.GL_RGB;

@Component
public class CubeMapManager {

    private final CubeMapService cubeMapService;
    private final ImageManager imageManager;

    @Wire
    public CubeMapManager(final CubeMapService cubeMapService, final ImageManager imageManager) {
        this.cubeMapService = cubeMapService;
        this.imageManager = imageManager;
    }

    public CubeMap supply(final String rightImage, final String leftImage, final String topImage,
                          final String bottomImage, final String backImage, final String frontImage) {
        ImageData right = imageManager.load(rightImage);
        ImageData left = imageManager.load(leftImage);
        ImageData top = imageManager.load(topImage);
        ImageData bottom = imageManager.load(bottomImage);
        ImageData back = imageManager.load(backImage);
        ImageData front = imageManager.load(frontImage);

        return supply(GL_RGB, right, left, top, bottom, back, front);
    }

    public CubeMap supply(final int format, final TextureData right, final TextureData left, final TextureData top,
                          final TextureData bottom, final TextureData back, final TextureData front) {
        return cubeMapService.supply(new CubeMapKey(format, right, left, top, bottom, back, front));
    }

    public CubeMap supply(final int format, final Vector2i resolution) {
        TextureData textureData = new TextureData(resolution.x, resolution.y, null);
        return supply(format, textureData, textureData, textureData, textureData, textureData, textureData);
    }
}
