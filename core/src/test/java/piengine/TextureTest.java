package piengine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piengine.visual.texture.accessor.TextureAccessor;
import piengine.visual.texture.domain.Texture;
import piengine.visual.texture.domain.TextureDao;
import piengine.visual.texture.domain.TextureData;
import piengine.visual.texture.interpreter.TextureInterpreter;
import piengine.visual.texture.manager.TextureManager;
import piengine.visual.texture.service.TextureService;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextureTest extends TestBase {

    private TextureManager textureManager;
    private TextureService textureService;
    private TextureAccessor textureAccessor;
    @Mock
    private TextureInterpreter textureInterpreter;
    @Mock
    private TextureDao dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        textureAccessor = new TextureAccessor();
        textureService = new TextureService(textureAccessor, textureInterpreter);
        textureManager = new TextureManager(textureService);
    }

    @Test
    public void supplyTest() throws Exception {
        ArgumentCaptor<TextureData> argumentCaptor = ArgumentCaptor.forClass(TextureData.class);
        when(textureInterpreter.create(argumentCaptor.capture())).thenReturn(dao);

        Texture texture = textureManager.supply("arrow");
        TextureData textureData = argumentCaptor.getValue();

        assertThat(texture.dao, equalTo(dao));
        assertThat(textureData.width, equalTo(16));
        assertThat(textureData.height, equalTo(16));
        assertThat(textureData.comp, equalTo(3));
    }

}
