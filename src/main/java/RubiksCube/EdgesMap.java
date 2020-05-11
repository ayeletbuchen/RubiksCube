package RubiksCube;

import java.util.HashMap;

public class EdgesMap extends HashMap<Square, Square> implements CubeValues {

    private Face upFace;
    private Face leftFace;
    private Face rightFace;
    private Face frontFace;
    private Face backFace;
    private Face downFace;

    public EdgesMap(Face upFace, Face leftFace, Face frontFace, Face rightFace, Face backFace, Face downFace) {
        this.upFace = upFace;
        this.leftFace = leftFace;
        this.frontFace = frontFace;
        this.rightFace = rightFace;
        this.backFace = backFace;
        this.downFace = downFace;
        setAdjacentEdgesMap();
    }

    private void setAdjacentEdgesMap() {
        put(downFace.squares[TOP_ROW][MIDDLE_COLUMN], frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        put(frontFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        put(leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        put(downFace.squares[MIDDLE_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        put(rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        put(downFace.squares[MIDDLE_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        put(backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        put(downFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], backFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        put(leftFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        put(upFace.squares[MIDDLE_ROW][LEFT_COLUMN], leftFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        put(frontFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN]);
        put(upFace.squares[BOTTOM_ROW][MIDDLE_COLUMN], frontFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        put(rightFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        put(upFace.squares[MIDDLE_ROW][RIGHT_COLUMN], rightFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        put(backFace.squares[TOP_ROW][MIDDLE_COLUMN], upFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        put(upFace.squares[TOP_ROW][MIDDLE_COLUMN], backFace.squares[TOP_ROW][MIDDLE_COLUMN]);
        put(leftFace.squares[MIDDLE_ROW][LEFT_COLUMN], backFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        put(backFace.squares[MIDDLE_ROW][RIGHT_COLUMN], leftFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        put(leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN], frontFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        put(frontFace.squares[MIDDLE_ROW][LEFT_COLUMN], leftFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        put(frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN], rightFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        put(rightFace.squares[MIDDLE_ROW][LEFT_COLUMN], frontFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
        put(rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN], backFace.squares[MIDDLE_ROW][LEFT_COLUMN]);
        put(backFace.squares[MIDDLE_ROW][LEFT_COLUMN], rightFace.squares[MIDDLE_ROW][RIGHT_COLUMN]);
    }
}
