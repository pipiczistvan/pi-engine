package piengine.io.interpreter.framebuffer;

import piengine.io.interpreter.Interpreter;
import piengine.io.interpreter.cubemap.CubeMap;
import piengine.io.interpreter.texture.Texture;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.COLOR;
import static piengine.io.interpreter.framebuffer.FramebufferAttachment.DEPTH;

public class Framebuffer implements Interpreter {

    private static final Stack<Integer> FRAMEBUFFER_STACK = new Stack<>();

    public final int id;
    public final int width;
    public final int height;
    private final Map<FramebufferAttachment, Texture> textureAttachments;
    private final Map<FramebufferAttachment, CubeMap> cubeMapAttachments;
    private final Map<FramebufferAttachment, Renderbuffer> renderbufferAttachments;

    public Framebuffer(final int width, final int height) {
        this.id = glGenFramebuffers();
        this.width = width;
        this.height = height;
        this.textureAttachments = new HashMap<>();
        this.cubeMapAttachments = new HashMap<>();
        this.renderbufferAttachments = new HashMap<>();
    }

    public Framebuffer bind() {
        FRAMEBUFFER_STACK.push(id);
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        return this;
    }

    public Framebuffer unbind() {
        FRAMEBUFFER_STACK.pop();
        rebindLast();

        return this;
    }

    @Override
    public void clear() {
        rebindLast();
        glDeleteFramebuffers(id);
        textureAttachments.values().forEach(Texture::clear);
        cubeMapAttachments.values().forEach(CubeMap::clear);
        renderbufferAttachments.values().forEach(Renderbuffer::clear);
    }

    public Framebuffer blit(final Framebuffer dest) {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, dest.id);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, id);
        glBlitFramebuffer(
                0, 0, width, height,
                0, 0, dest.width, dest.height,
                GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT, GL_NEAREST);
        rebindLast();

        return this;
    }

    public Framebuffer setDrawBuffer(final boolean drawingEnabled) {
        glDrawBuffer(drawingEnabled ? GL_COLOR_ATTACHMENT0 : GL_NONE);

        return this;
    }

    public Framebuffer setReadBuffer(final boolean drawingEnabled) {
        glReadBuffer(drawingEnabled ? GL_COLOR_ATTACHMENT0 : GL_NONE);

        return this;
    }

    public Framebuffer attachColorTexture() {
        Texture texture = new Texture(width, height)
                .bind(GL_TEXTURE0)
                .attachBuffer(GL_RGBA8, GL_UNSIGNED_BYTE, null)
                .attachParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                .attachParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                .unbind();

        return attachColorTexture(texture);
    }

    public Framebuffer attachColorTexture(final Texture texture) {
        textureAttachments.put(COLOR, texture);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture.id, 0);

        return this;
    }

    public Framebuffer attachDepthTexture() {
        Texture texture = new Texture(width, height)
                .bind(GL_TEXTURE0)
                .attachBuffer(GL_DEPTH_COMPONENT24, GL_FLOAT, null)
                .attachParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                .attachParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                .unbind();

        return attachDepthTexture(texture);
    }

    public Framebuffer attachDepthTexture(final Texture texture) {
        textureAttachments.put(DEPTH, texture);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture.id, 0);

        return this;
    }

    public Framebuffer attachDepthCubeMap() {
        CubeMap cubeMap = new CubeMap()
                .bind(GL_TEXTURE0)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL_DEPTH_COMPONENT24, GL_FLOAT, width, height, null)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL_DEPTH_COMPONENT24, GL_FLOAT, width, height, null)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL_DEPTH_COMPONENT24, GL_FLOAT, width, height, null)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_DEPTH_COMPONENT24, GL_FLOAT, width, height, null)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, GL_DEPTH_COMPONENT24, GL_FLOAT, width, height, null)
                .attachBuffer(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, GL_DEPTH_COMPONENT24, GL_FLOAT, width, height, null)
                .attachParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
                .attachParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
                .attachParameter(GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE)
                .attachParameter(GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                .attachParameter(GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                .unbind();

        return attachDepthCubeMap(cubeMap);
    }

    public Framebuffer attachDepthCubeMap(final CubeMap cubeMap) {
        cubeMapAttachments.put(DEPTH, cubeMap);
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, cubeMap.id, 0);

        return this;
    }

    public Framebuffer attachDepthBuffer() {
        Renderbuffer renderbuffer = new Renderbuffer(width, height)
                .bind()
                .attachStorage(GL_DEPTH_COMPONENT24);

        renderbufferAttachments.put(DEPTH, renderbuffer);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderbuffer.id);

        renderbuffer.unbind();

        return this;
    }

    public Framebuffer attachDepthBuffer(final int samples) {
        Renderbuffer renderbuffer = new Renderbuffer(width, height)
                .bind()
                .attachStorage(GL_DEPTH_COMPONENT24, samples);

        renderbufferAttachments.put(DEPTH, renderbuffer);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderbuffer.id);

        renderbuffer.unbind();

        return this;
    }

    public Framebuffer attachColorBuffer(final int samples) {
        Renderbuffer renderbuffer = new Renderbuffer(width, height)
                .bind()
                .attachStorage(GL_RGBA8, samples);

        renderbufferAttachments.put(COLOR, renderbuffer);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, renderbuffer.id);

        renderbuffer.unbind();

        return this;
    }

    public Texture getTextureAttachment(final FramebufferAttachment attachment) {
        return textureAttachments.get(attachment);
    }

    public CubeMap getCubeMapAttachment(final FramebufferAttachment attachment) {
        return cubeMapAttachments.get(attachment);
    }

    public Renderbuffer getRenderbufferAttachment(final FramebufferAttachment attachment) {
        return renderbufferAttachments.get(attachment);
    }

    private static void rebindLast() {
        if (FRAMEBUFFER_STACK.empty()) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
        } else {
            glBindFramebuffer(GL_FRAMEBUFFER, FRAMEBUFFER_STACK.peek());
        }
    }
}
