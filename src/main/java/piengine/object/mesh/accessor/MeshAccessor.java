package piengine.object.mesh.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.object.mesh.domain.MeshData;
import piengine.object.mesh.domain.ParsedMeshData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static piengine.core.property.domain.ApplicationProperties.get;
import static piengine.core.property.domain.PropertyKeys.MESHES_LOCATION;

@Component
public class MeshAccessor implements Accessor<MeshData> {

    private static final String ROOT = get(MESHES_LOCATION);
    private static final String MESH_EXT = "obj";

    private final MeshParser meshParser;
    private final ResourceLoader loader;

    @Wire
    public MeshAccessor(final MeshParser meshParser) {
        this.meshParser = meshParser;
        this.loader = new ResourceLoader(ROOT, MESH_EXT);
    }

    @Override
    public MeshData access(final String file) {
        final String meshSource = loader.load(file);
        final ParsedMeshData parsedMeshData = meshParser.parseSource(meshSource.split("\n"));

        return new MeshData(parsedMeshData.vertices, parsedMeshData.indices, parsedMeshData.textureCoords);
    }

}
