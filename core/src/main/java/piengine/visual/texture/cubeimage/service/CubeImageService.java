package piengine.visual.texture.cubeimage.service;


import piengine.core.architecture.service.SupplierService;
import piengine.io.interpreter.cubemap.CubeMap;
import piengine.io.loader.png.domain.PngDto;
import piengine.io.loader.png.loader.PngLoader;
import piengine.visual.texture.cubeimage.domain.CubeImage;
import piengine.visual.texture.cubeimage.domain.CubeImageKey;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;

@Component
public class CubeImageService extends SupplierService<CubeImageKey, CubeImage> {

    private final PngLoader pngLoader;
    private final Map<CubeImageKey, CubeImage> cubeImageMap;

    @Wire
    public CubeImageService(final PngLoader pngLoader) {
        this.pngLoader = pngLoader;
        this.cubeImageMap = new HashMap<>();
    }

    @Override
    public CubeImage supply(final CubeImageKey key) {
        return cubeImageMap.computeIfAbsent(key, this::createCubeImage);
    }

    @Override
    public void terminate() {
        cubeImageMap.values().forEach(CubeMap::clear);
    }

    private CubeImage createCubeImage(final CubeImageKey cubeImageKey) {
        PngDto right = pngLoader.load(cubeImageKey.right);
        PngDto left = pngLoader.load(cubeImageKey.left);
        PngDto top = pngLoader.load(cubeImageKey.top);
        PngDto bottom = pngLoader.load(cubeImageKey.bottom);
        PngDto back = pngLoader.load(cubeImageKey.back);
        PngDto front = pngLoader.load(cubeImageKey.front);

        return (CubeImage) new CubeImage()
                .bind(GL_TEXTURE0)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL_RGB8, GL_UNSIGNED_BYTE, right.width, right.height, right.buffer)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL_RGB8, GL_UNSIGNED_BYTE, left.width, left.height, left.buffer)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL_RGB8, GL_UNSIGNED_BYTE, top.width, top.height, top.buffer)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_RGB8, GL_UNSIGNED_BYTE, bottom.width, bottom.height, bottom.buffer)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, GL_RGB8, GL_UNSIGNED_BYTE, back.width, back.height, back.buffer)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, GL_RGB8, GL_UNSIGNED_BYTE, front.width, front.height, front.buffer)
                .attachParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
                .attachParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
                .attachParameter(GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE)
                .attachParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                .attachParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                .unbind();
    }
}
