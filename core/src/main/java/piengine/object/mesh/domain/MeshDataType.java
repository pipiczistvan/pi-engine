package piengine.object.mesh.domain;

public enum MeshDataType {

    VERTEX(0),
    TEXTURE_COORD(1),
    COLOR(2),
    NORMAL(3),
    INDICATOR(4),
    JOINT_INDEX(5),
    WEIGHT(6);

    public final int value;

    MeshDataType(int value) {
        this.value = value;
    }

}
