package piengine.io.loader.text.domain;

public class Character {

    public final int id;
    public final double xTextureCoord;
    public final double yTextureCoord;
    public final double xMaxTextureCoord;
    public final double yMaxTextureCoord;
    public final double xOffset;
    public final double yOffset;
    public final double sizeX;
    public final double sizeY;
    public final double xAdvance;

    public Character(final int id, final double xTextureCoord,
                     final double yTextureCoord, final double xTexSize,
                     final double yTexSize, final double xOffset,
                     final double yOffset, final double sizeX,
                     final double sizeY, final double xAdvance) {
        this.id = id;
        this.xTextureCoord = xTextureCoord;
        this.yTextureCoord = yTextureCoord;
        this.xMaxTextureCoord = xTexSize + xTextureCoord;
        this.yMaxTextureCoord = yTexSize + yTextureCoord;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.xAdvance = xAdvance;
    }

}
