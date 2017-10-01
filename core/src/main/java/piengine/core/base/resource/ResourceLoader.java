package piengine.core.base.resource;

import jdk.nashorn.api.scripting.URLReader;
import org.lwjgl.BufferUtils;
import piengine.core.base.exception.PIEngineException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class ResourceLoader {

    private final String root;
    private final String extension;

    public ResourceLoader(final String root, final String extension) {
        this.root = root;
        this.extension = extension;
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

    public URL getUrl(final String file) {
        String fullPath = createFilePath(root, file, extension);
        return getClass().getResource(fullPath);
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

}
