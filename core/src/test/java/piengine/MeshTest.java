package piengine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import piengine.object.mesh.accessor.MeshAccessor;
import piengine.object.mesh.accessor.ObjParser;
import piengine.object.mesh.domain.Mesh;
import piengine.object.mesh.domain.MeshDao;
import piengine.object.mesh.domain.MeshData;
import piengine.object.mesh.interpreter.MeshInterpreter;
import piengine.object.mesh.manager.MeshManager;
import piengine.object.mesh.service.MeshService;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeshTest extends TestBase {

    private MeshManager meshManager;
    private MeshService meshService;
    private MeshAccessor meshAccessor;
    private ObjParser objParser;
    @Mock
    private MeshInterpreter meshInterpreter;
    @Mock
    private MeshDao dao;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        objParser = new ObjParser();
        meshAccessor = new MeshAccessor(objParser);
        meshService = new MeshService(meshAccessor, meshInterpreter);
        meshManager = new MeshManager(meshService);
    }

    @Test
    public void supplyTest() throws Exception {
        ArgumentCaptor<MeshData> argumentCaptor = ArgumentCaptor.forClass(MeshData.class);
        when(meshInterpreter.create(argumentCaptor.capture())).thenReturn(dao);

        Mesh mesh = meshManager.supply("canvas");
        MeshData meshData = argumentCaptor.getValue();

        assertThat(mesh.getDao(), equalTo(dao));
        assertThat(meshData.vertices, equalTo(getVertices()));
        assertThat(meshData.indices, equalTo(getIndices()));
        assertThat(meshData.textureCoords, equalTo(getTextureCoords()));
    }

    private float[] getVertices() {
        return new float[]{-1.0f, -1.0f, -0.0f, 1.0f, -1.0f, -0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
    }

    private int[] getIndices() {
        return new int[]{1, 2, 0, 1, 3, 2};
    }

    private float[] getTextureCoords() {
        return new float[]{1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};
    }

}
