package piengine.planet.accessor;

import piengine.core.base.api.Accessor;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.property.domain.ApplicationProperties;
import piengine.core.property.domain.PropertyKeys;
import piengine.object.mesh.accessor.MeshParser;
import piengine.object.mesh.domain.ParsedMeshData;
import piengine.planet.domain.PlanetData;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

@Component
public class PlanetAccessor implements Accessor<PlanetData> {

    private static final String ROOT = ApplicationProperties.get(PropertyKeys.MESHES_LOCATION);
    private static final String MESH_EXT = "obj";

    private final MeshParser meshParser;
    private final ResourceLoader loader;

    @Wire
    public PlanetAccessor(final MeshParser meshParser) {
        this.meshParser = meshParser;
        this.loader = new ResourceLoader(ROOT, MESH_EXT);
    }


    @Override
    public PlanetData access(final String file) {
        final String meshSource = loader.load(file);
        final ParsedMeshData parsedMeshData = meshParser.parseSource(meshSource.split("\n"));

        return new PlanetData(parsedMeshData.vertices, parsedMeshData.indices);
    }

}
