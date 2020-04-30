package RubiksCube;

import java.util.HashMap;

public class CornersMap extends HashMap<Square, Square[]> {

    private Face upFace;
    private Face leftFace;
    private Face frontFace;
    private Face rightFace;
    private Face backFace;
    private Face downFace;
    private final int TOP_ROW = CubeValues.TOP_ROW.getValue();
    private final int BOTTOM_ROW = CubeValues.BOTTOM_ROW.getValue();
    private final int LEFT_COLUMN = CubeValues.LEFT_COLUMN.getValue();
    private final int RIGHT_COLUMN = CubeValues.RIGHT_COLUMN.getValue();

    public CornersMap(Face upFace, Face leftFace, Face frontFace, Face rightFace, Face backFace, Face downFace) {
        this.upFace = upFace;
        this.leftFace = leftFace;
        this.frontFace = frontFace;
        this.rightFace = rightFace;
        this.backFace = backFace;
        this.downFace = downFace;
        setAdjacentCornersMap();
    }

    private void setAdjacentCornersMap() {
        put(downFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {frontFace.squares[BOTTOM_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        put(frontFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[TOP_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        put(leftFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {frontFace.squares[BOTTOM_ROW][LEFT_COLUMN], downFace.squares[TOP_ROW][LEFT_COLUMN]});

        put(downFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        put(frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {downFace.squares[TOP_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        put(rightFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[TOP_ROW][RIGHT_COLUMN], frontFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});

        put(downFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {leftFace.squares[BOTTOM_ROW][LEFT_COLUMN], backFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        put(leftFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][LEFT_COLUMN], backFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});
        put(backFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][LEFT_COLUMN], leftFace.squares[BOTTOM_ROW][LEFT_COLUMN]});

        put(downFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {rightFace.squares[BOTTOM_ROW][RIGHT_COLUMN], backFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        put(rightFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][RIGHT_COLUMN], backFace.squares[BOTTOM_ROW][LEFT_COLUMN]});
        put(backFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {downFace.squares[BOTTOM_ROW][RIGHT_COLUMN], rightFace.squares[BOTTOM_ROW][RIGHT_COLUMN]});

        put(upFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {leftFace.squares[TOP_ROW][LEFT_COLUMN], backFace.squares[TOP_ROW][RIGHT_COLUMN]});
        put(leftFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][LEFT_COLUMN], backFace.squares[TOP_ROW][RIGHT_COLUMN]});
        put(backFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][LEFT_COLUMN], leftFace.squares[TOP_ROW][LEFT_COLUMN]});

        put(upFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {rightFace.squares[TOP_ROW][RIGHT_COLUMN], backFace.squares[TOP_ROW][LEFT_COLUMN]});
        put(rightFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][RIGHT_COLUMN], backFace.squares[TOP_ROW][LEFT_COLUMN]});
        put(backFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[TOP_ROW][RIGHT_COLUMN], rightFace.squares[TOP_ROW][RIGHT_COLUMN]});

        put(upFace.squares[BOTTOM_ROW][LEFT_COLUMN],
                new Square[] {leftFace.squares[TOP_ROW][RIGHT_COLUMN], frontFace.squares[TOP_ROW][LEFT_COLUMN]});
        put(leftFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][LEFT_COLUMN], frontFace.squares[TOP_ROW][LEFT_COLUMN]});
        put(frontFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][LEFT_COLUMN], leftFace.squares[TOP_ROW][RIGHT_COLUMN]});

        put(upFace.squares[BOTTOM_ROW][RIGHT_COLUMN],
                new Square[] {rightFace.squares[TOP_ROW][LEFT_COLUMN], frontFace.squares[TOP_ROW][RIGHT_COLUMN]});
        put(rightFace.squares[TOP_ROW][LEFT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][RIGHT_COLUMN], frontFace.squares[TOP_ROW][RIGHT_COLUMN]});
        put(frontFace.squares[TOP_ROW][RIGHT_COLUMN],
                new Square[] {upFace.squares[BOTTOM_ROW][RIGHT_COLUMN], rightFace.squares[TOP_ROW][LEFT_COLUMN]});
    }
}
