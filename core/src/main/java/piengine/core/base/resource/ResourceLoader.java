package piengine.core.base.resource;

import jdk.nashorn.api.scripting.URLReader;
import org.lwjgl.BufferUtils;
import piengine.core.base.exception.PIEngineException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.RESOURCES_LOCATION;

public class ResourceLoader {

    private static final String USER_DIR = Objects.requireNonNull(ResourceLoader.class.getClassLoader().getResource("")).getPath();

    private final String root;
    private final String extension;

    public ResourceLoader(final String root, final String extension) {
        this.root = root;
        this.extension = extension;
    }

    private static String createFilePath(final String root, final String file, final String extension) {
        return String.format("/%s/%s.%s", root, file, extension);
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public String load(final String file) {
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new URLReader(getUrl(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            throw new PIEngineException("Could not find file %s!", file, e);
        }

        return builder.toString();
    }

    public ByteBuffer loadByteBuffer(final String file, final int bufferSize) throws IOException, URISyntaxException {
        ByteBuffer buffer;

        URL url = getUrl(file);
        Path path = Paths.get(url.toURI());
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int) fc.size() + 1);
                //noinspection StatementWithEmptyBody
                while (fc.read(buffer) != -1) {
                }
            }
        } else {
            try (
                    InputStream source = url.openStream();
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    public BufferedImage loadBufferedImage(final String file) throws IOException {
        return ImageIO.read(getUrl(file));
    }

    public URL getUrl(final String file) {
        String classPathFilePath = createFilePath(root, file, extension);
        URL classPathFileUrl = getClass().getResource(classPathFilePath);
        if (classPathFileUrl != null) {
            return classPathFileUrl;
        }

        String resourceFileFullPath = String.format("%s%s/%s/%s.%s", USER_DIR, get(RESOURCES_LOCATION), root, file, extension);
        File f = new File(resourceFileFullPath);
        if (f.exists()) {
            try {
                return f.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new PIEngineException(e);
            }
        }

        throw new PIEngineException("Could not find file: %s", f.getAbsolutePath());
    }

}
