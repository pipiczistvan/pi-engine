package piengine.visual.writing.text.domain;

import piengine.core.base.domain.Dao;

import java.util.List;

public class TextDao implements Dao {

    //todo: final
    public  int vaoId;
    public  List<Integer> vboIds;
    public  int vertexCount;

    public TextDao(final int vaoId, final List<Integer> vboIds, final int vertexCount) {
        this.vaoId = vaoId;
        this.vboIds = vboIds;
        this.vertexCount = vertexCount;
    }

}
