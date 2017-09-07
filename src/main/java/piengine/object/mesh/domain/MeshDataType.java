package piengine.object.mesh.domain;

public enum MeshDataType {

    VERTEX(0),
    TEXTURE_COORD(1);

    public final int value;

    MeshDataType(int value) {
        this.value = value;
    }

}
