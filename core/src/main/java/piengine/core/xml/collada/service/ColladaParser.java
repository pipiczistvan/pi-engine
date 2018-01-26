package piengine.core.xml.collada.service;

import piengine.core.base.exception.PIEngineException;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.xml.collada.domain.Collada;
import puppeteer.annotation.premade.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.COLLADA_LOCATION;

@Component
public class ColladaParser {

    private static final String ROOT = get(COLLADA_LOCATION);
    private static final String COLLADA_EXT = "dae";

    private final ResourceLoader loader;
    private final Unmarshaller unmarshaller;

    public ColladaParser() throws JAXBException {
        this.loader = new ResourceLoader(ROOT, COLLADA_EXT);
        this.unmarshaller = JAXBContext.newInstance(Collada.class).createUnmarshaller();
    }

    public Collada parse(final String file) {
        try {
            return (Collada) unmarshaller.unmarshal(loader.getUrl(file));
        } catch (JAXBException e) {
            throw new PIEngineException("Could not unmarshal collada file: %s!", file);
        }
    }
}
