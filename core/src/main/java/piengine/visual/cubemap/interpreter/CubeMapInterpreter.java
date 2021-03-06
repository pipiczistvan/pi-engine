package piengine.visual.cubemap.interpreter;

import piengine.core.base.api.Interpreter;
import piengine.visual.cubemap.domain.CubeMapDao;
import piengine.visual.cubemap.domain.CubeMapData;
import piengine.visual.texture.domain.TextureData;
import puppeteer.annotation.premade.Component;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.glActiveTexture;

@Component
public class CubeMapInterpreter extends Interpreter<CubeMapData, CubeMapDao> {

    @Override
    protected CubeMapDao createDao(final CubeMapData cubeMapData) {
        CubeMapDao dao = new CubeMapDao(glGenTextures());

        glEnable(GL_TEXTURE_CUBE_MAP);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, dao.getTexture());

        for (int i = 0; i < cubeMapData.textureData.length; i++) {
            TextureData textureData = cubeMapData.textureData[i];
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, cubeMapData.format, textureData.width, textureData.height, 0, cubeMapData.format, cubeMapData.type, textureData.buffer);
        }

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);

        return dao;
    }

    @Override
    protected boolean freeDao(final CubeMapDao dao) {
        glDeleteTextures(dao.getTexture());

        return true;
    }

    @Override
    protected String getCreateInfo(final CubeMapDao dao, final CubeMapData resource) {
        return String.format("Texture id: %s", dao.getTexture());
    }

    @Override
    protected String getFreeInfo(final CubeMapDao dao) {
        return String.format("Texture id: %s", dao.getTexture());
    }
}
