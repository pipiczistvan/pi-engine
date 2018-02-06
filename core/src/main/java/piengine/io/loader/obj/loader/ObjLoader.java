package piengine.io.loader.obj.loader;

import piengine.io.loader.AbstractLoader;
import piengine.io.loader.ResourceLoader;
import piengine.io.loader.obj.domain.ObjDto;
import piengine.io.loader.obj.parser.ObjParser;
import puppeteer.annotation.premade.Component;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.MESHES_LOCATION;

@Component
public class ObjLoader extends AbstractLoader<String, ObjDto> {

    private static final String ROOT = get(MESHES_LOCATION);
    private static final String MESH_EXT = "obj";

    private final ObjParser objParser;
    private final ResourceLoader resourceLoader;

    public ObjLoader() {
        this.objParser = new ObjParser();
        this.resourceLoader = new ResourceLoader(ROOT, MESH_EXT);
    }

    @Override
    protected ObjDto createDto(final String key) {
        String[] source = resourceLoader.load(key).split("\n");

        return objParser.parseSource(source);
    }

}
