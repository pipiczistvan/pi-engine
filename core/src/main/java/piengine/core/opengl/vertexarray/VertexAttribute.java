package piengine.core.opengl.vertexarray;

public enum VertexAttribute {

    INDEX(-1),
    EMPTY(-1),
    VERTEX(0),
    TEXTURE_COORD(1),
    COLOR(2),
    NORMAL(3),
    INDICATOR(4),
    JOINT_INDEX(5),
    WEIGHT(6),
    MODEL_VIEW_MATRIX_1(7),
    MODEL_VIEW_MATRIX_2(8),
    MODEL_VIEW_MATRIX_3(9),
    MODEL_VIEW_MATRIX_4(10),
    TEXTURE_OFFSET(11),
    BLEND(12);

    public final int index;

    VertexAttribute(int value) {
        this.index = value;
    }
}
