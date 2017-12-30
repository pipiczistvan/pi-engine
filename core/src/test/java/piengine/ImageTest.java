package piengine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piengine.visual.image.accessor.ImageAccessor;
import piengine.visual.image.domain.Image;
import piengine.visual.image.domain.ImageDao;
import piengine.visual.image.domain.ImageData;
import piengine.visual.image.interpreter.ImageInterpreter;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.image.service.ImageService;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageTest extends TestBase {

    private ImageManager imageManager;
    @Mock
    private ImageInterpreter imageInterpreter;
    @Mock
    private ImageDao dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        ImageAccessor imageAccessor = new ImageAccessor();
        ImageService imageService = new ImageService(imageAccessor, imageInterpreter);
        imageManager = new ImageManager(imageService);
    }

    @Test
    public void supplyTest() {
        ArgumentCaptor<ImageData> argumentCaptor = ArgumentCaptor.forClass(ImageData.class);
        when(imageInterpreter.create(argumentCaptor.capture())).thenReturn(dao);

        Image image = imageManager.supply("arrow");
        ImageData imageData = argumentCaptor.getValue();

        assertThat(image.dao, equalTo(dao));
        assertThat(imageData.width, equalTo(16));
        assertThat(imageData.height, equalTo(16));
        assertThat(imageData.comp, equalTo(3));
    }
}
