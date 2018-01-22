package piengine.object.mesh.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.object.mesh.domain.MeshData;
import piengine.object.mesh.domain.MeshKey;
import piengine.object.mesh.domain.ParsedMeshData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.MESHES_LOCATION;

@Component
public class MeshAccessor implements Accessor<MeshKey, MeshData> {

    private static final String ROOT = get(MESHES_LOCATION);
    private static final String MESH_EXT = "obj";

    private final ObjParser objParser;
    private final ResourceLoader loader;

    @Wire
    public MeshAccessor(final ObjParser objParser) {
        this.objParser = objParser;
        this.loader = new ResourceLoader(ROOT, MESH_EXT);
    }

    @Override
    public MeshData access(final MeshKey key) {
        final String meshSource = loader.load(key.file);
        final ParsedMeshData parsedMeshData = objParser.parseSource(meshSource.split("\n"));

        return new MeshData(parsedMeshData.vertices, parsedMeshData.indices, parsedMeshData.textureCoords, parsedMeshData.normals);
    }
}
