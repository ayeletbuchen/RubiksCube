import java.util.Arrays;

public class Cube {

    private Face topFace;
    private Face leftFace;
    private Face rightFace;
    private Face frontFace;
    private Face backFace;
    private Face downFace;

    public Cube(Face topFace, Face leftFace, Face rightFace, Face frontFace, Face backFace, Face downFace) {
        this.topFace = topFace;
        this.leftFace = leftFace;
        this.rightFace = rightFace;
        this.frontFace = frontFace;
        this.backFace = backFace;
        this.downFace = downFace;
    }

    public void rotateTopFaceClockwise() {
        topFace.rotateClockwise();
        rotateHorizontalRingClockwise(CubeInfo.TOP_ROW.getValue());
    }

    public void rotateTopFaceCounterclockwise() {
        topFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeInfo.TOP_ROW.getValue());
    }

    public void rotateLeftFaceClockwise() {
        leftFace.rotateClockwise();
        rotateVerticalRingDownwards(CubeInfo.LEFT_COLUMN.getValue());
    }

    public void rotateLeftFaceCounterclockwise() {
        leftFace.rotateCounterclockwise();
        rotateVerticalRingUpwards(CubeInfo.LEFT_COLUMN.getValue());
    }

    public void rotateRightFaceClockwise() {
        rightFace.rotateClockwise();
        rotateVerticalRingUpwards(CubeInfo.RIGHT_COLUMN.getValue());
    }

    public void rotateRightFaceCounterclockwise() {
        rightFace.rotateCounterclockwise();
        rotateVerticalRingDownwards(CubeInfo.RIGHT_COLUMN.getValue());
    }

    public void rotateFrontFaceClockwise() {

    }

    public void rotateFrontFaceCounterclockwise() {

    }

    public void rotateBackFaceClockwise() {

    }

    public void rotateBackFaceCounterclockwise() {

    }

    public void rotateDownFaceClockwise() {
        downFace.rotateClockwise();
        rotateHorizontalRingClockwise(CubeInfo.BOTTOM_ROW.getValue());
    }

    public void rotateDownFaceCounterclockwise() {
        downFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeInfo.BOTTOM_ROW.getValue());
    }

    public void sliceMiddleColumnsUpwards() {
        rotateVerticalRingUpwards(CubeInfo.MIDDLE_COLUMN.getValue());
    }

    public void sliceMiddleColumnsDownwards() {
        rotateVerticalRingDownwards(CubeInfo.MIDDLE_COLUMN.getValue());
    }

    public void sliceMiddleRowsClockwise() {
        rotateHorizontalRingClockwise(CubeInfo.MIDDLE_ROW.getValue());
    }

    public void sliceMiddleRowsCounterclockwise() {
        rotateHorizontalRingCounterclockwise(CubeInfo.MIDDLE_ROW.getValue());
    }

    public void sliceMiddleRowsAndColumnsRight() {

    }

    public void sliceMiddleRowsAndColumnsLeft() {

    }

    private void rotateHorizontalRingClockwise(int row) {
        Square[] frontRow = frontFace.getRow(row);
        Square[] rightRow = rightFace.getRow(row);
        Square[] backRow = backFace.getRow(row);
        Square[] leftRow = leftFace.getRow(row);

        Square[] tempRow = Arrays.copyOf(frontRow, frontFace.SIZE);

        frontFace.setRow(row, rightRow);
        rightFace.setRow(row, backRow);
        backFace.setRow(row, leftRow);
        leftFace.setRow(row, tempRow);
    }

    private void rotateHorizontalRingCounterclockwise(int row) {
        Square[] frontRow = frontFace.getRow(row);
        Square[] rightRow = rightFace.getRow(row);
        Square[] backRow = backFace.getRow(row);
        Square[] leftRow = leftFace.getRow(row);

        Square[] tempRow = Arrays.copyOf(frontRow, frontFace.SIZE);

        frontFace.setRow(row, leftRow);
        leftFace.setRow(row, backRow);
        backFace.setRow(row, rightRow);
        rightFace.setRow(row, tempRow);
    }

    private void rotateVerticalRingUpwards(int column) {
        Square[] topColumn = topFace.getColumn(column);
        Square[] backColumn = backFace.getColumn(column);
        Square[] downColumn = downFace.getColumn(column);
        Square[] frontColumn = frontFace.getColumn(column);

        topFace.setColumn(column, frontColumn);
        frontFace.setColumn(column, downColumn);
        downFace.setColumn(column, backColumn);
        backFace.setColumn(column, topColumn);
    }

    public void rotateRingOfRowsAndColumns(boolean clockwise, int topFaceRow) {
        int top;
        int down;
        int left;
        int right;

        top = topFaceRow;

        if (topFaceRow == CubeInfo.TOP_ROW.getValue()) {
            right = CubeInfo.RIGHT_COLUMN.getValue();
            down = CubeInfo.BOTTOM_ROW.getValue();
            left = CubeInfo.LEFT_COLUMN.getValue();
        } else if (topFaceRow == CubeInfo.MIDDLE_ROW.getValue()) {
            right = CubeInfo.MIDDLE_COLUMN.getValue();
            down = CubeInfo.MIDDLE_ROW.getValue();
            left = CubeInfo.MIDDLE_COLUMN.getValue();
        } else { // Bottom row
            right = CubeInfo.LEFT_COLUMN.getValue();
            down = CubeInfo.TOP_ROW.getValue();
            left = CubeInfo.RIGHT_COLUMN.getValue();
        }

        if (clockwise) {
            rotateRingOfRowsAndColumnsClockwise(top, right, down, left);
        } else {
            rotateRingOfRowsAndColumnsCounterclockwise(top, right, down, left);
        }
    }

    private void rotateRingOfRowsAndColumnsClockwise(int topRow, int rightColumn, int downRow, int leftColumn) {
        Square[] temp = Arrays.copyOf(topFace.getRow(topRow), topFace.SIZE);

        topFace.setRow(topRow, leftFace.getColumn(leftColumn));
        leftFace.setColumn(leftColumn, downFace.getRow(downRow));
        downFace.setRow(downRow, rightFace.getColumn(rightColumn));
        rightFace.setColumn(rightColumn, temp);
    }

    private void rotateRingOfRowsAndColumnsCounterclockwise(int topRow, int rightColumn, int downRow, int leftColumn) {
        Square[] temp = Arrays.copyOf(topFace.getRow(topRow), topFace.SIZE);

        topFace.setRow(topRow, rightFace.getColumn(rightColumn));
        rightFace.setColumn(rightColumn, downFace.getRow(downRow));
        downFace.setRow(downRow, leftFace.getColumn(leftColumn));
        leftFace.setColumn(leftColumn, temp);
    }

    private void rotateVerticalRingDownwards(int column) {
        Square[] topColumn = topFace.getColumn(column);
        Square[] backColumn = backFace.getColumn(column);
        Square[] downColumn = downFace.getColumn(column);
        Square[] frontColumn = frontFace.getColumn(column);

        topFace.setColumn(column, backColumn);
        backFace.setColumn(column, downColumn);
        downFace.setColumn(column, frontColumn);
        frontFace.setColumn(column, topColumn);
    }

    public void turnCubeRight() { // TODO LOOK THIS OVER
        topFace.rotateCounterclockwise();
        Square[][] frontFaceCopy = frontFace.deepCopy();
        frontFace = leftFace;
        leftFace = backFace;
        backFace = rightFace;
        rightFace.setRow(CubeInfo.TOP_ROW.getValue(), frontFaceCopy[CubeInfo.TOP_ROW.getValue()]);
        rightFace.setRow(CubeInfo.MIDDLE_ROW.getValue(), frontFaceCopy[CubeInfo.MIDDLE_ROW.getValue()]);
        rightFace.setRow(CubeInfo.BOTTOM_ROW.getValue(), frontFaceCopy[CubeInfo.BOTTOM_ROW.getValue()]);
        downFace.rotateCounterclockwise();
    }

    public void turnCubeLeft() { // TODO LOOK THIS OVER
        topFace.rotateClockwise();
        Square[][] frontFaceCopy = frontFace.deepCopy();
        frontFace = rightFace;
        rightFace = backFace;
        backFace = leftFace;
        leftFace.setRow(CubeInfo.TOP_ROW.getValue(), frontFaceCopy[CubeInfo.TOP_ROW.getValue()]);
        leftFace.setRow(CubeInfo.MIDDLE_ROW.getValue(), frontFaceCopy[CubeInfo.MIDDLE_ROW.getValue()]);
        leftFace.setRow(CubeInfo.BOTTOM_ROW.getValue(), frontFaceCopy[CubeInfo.BOTTOM_ROW.getValue()]);
        downFace.rotateCounterclockwise();
    }

    public void shuffle() {

    }
}
