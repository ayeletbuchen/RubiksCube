import java.util.Arrays;

public class Cube {

    private Face topFace;
    private Face leftFace;
    private Face rightFace;
    private Face frontFace;
    private Face backFace;
    private Face downFace;

    public Cube() {
        frontFace = new Face(CubeColors.WHITE.getColor());
        leftFace = new Face(CubeColors.BLUE.getColor());
        topFace = new Face(CubeColors.RED.getColor());
        downFace = new Face(CubeColors.ORANGE.getColor());
        backFace = new Face(CubeColors.YELLOW.getColor());
        rightFace = new Face(CubeColors.GREEN.getColor());
    }

    public void rotateTopFaceClockwise() {
        topFace.rotateClockwise();
        rotateHorizontalRingClockwise(CubeValues.TOP_ROW.getValue());
    }

    public void rotateTopFaceCounterclockwise() {
        topFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeValues.TOP_ROW.getValue());
    }

    public void rotateLeftFaceClockwise() {
        leftFace.rotateClockwise();
        rotateVerticalRingDownwards(CubeValues.LEFT_COLUMN.getValue());
    }

    public void rotateLeftFaceCounterclockwise() {
        leftFace.rotateCounterclockwise();
        rotateVerticalRingUpwards(CubeValues.LEFT_COLUMN.getValue());
    }

    public void rotateRightFaceClockwise() {
        rightFace.rotateClockwise();
        rotateVerticalRingUpwards(CubeValues.RIGHT_COLUMN.getValue());
    }

    public void rotateRightFaceCounterclockwise() {
        rightFace.rotateCounterclockwise();
        rotateVerticalRingDownwards(CubeValues.RIGHT_COLUMN.getValue());
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
        rotateHorizontalRingClockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateDownFaceCounterclockwise() {
        downFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void sliceMiddleColumnsUpwards() {
        rotateVerticalRingUpwards(CubeValues.MIDDLE_COLUMN.getValue());
    }

    public void sliceMiddleColumnsDownwards() {
        rotateVerticalRingDownwards(CubeValues.MIDDLE_COLUMN.getValue());
    }

    public void sliceMiddleRowsClockwise() {
        rotateHorizontalRingClockwise(CubeValues.MIDDLE_ROW.getValue());
    }

    public void sliceMiddleRowsCounterclockwise() {
        rotateHorizontalRingCounterclockwise(CubeValues.MIDDLE_ROW.getValue());
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

        if (topFaceRow == CubeValues.TOP_ROW.getValue()) {
            right = CubeValues.RIGHT_COLUMN.getValue();
            down = CubeValues.BOTTOM_ROW.getValue();
            left = CubeValues.LEFT_COLUMN.getValue();
        } else if (topFaceRow == CubeValues.MIDDLE_ROW.getValue()) {
            right = CubeValues.MIDDLE_COLUMN.getValue();
            down = CubeValues.MIDDLE_ROW.getValue();
            left = CubeValues.MIDDLE_COLUMN.getValue();
        } else { // Bottom row
            right = CubeValues.LEFT_COLUMN.getValue();
            down = CubeValues.TOP_ROW.getValue();
            left = CubeValues.RIGHT_COLUMN.getValue();
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
        rightFace.setRow(CubeValues.TOP_ROW.getValue(), frontFaceCopy[CubeValues.TOP_ROW.getValue()]);
        rightFace.setRow(CubeValues.MIDDLE_ROW.getValue(), frontFaceCopy[CubeValues.MIDDLE_ROW.getValue()]);
        rightFace.setRow(CubeValues.BOTTOM_ROW.getValue(), frontFaceCopy[CubeValues.BOTTOM_ROW.getValue()]);
        downFace.rotateCounterclockwise();
    }

    public void turnCubeLeft() { // TODO LOOK THIS OVER
        topFace.rotateClockwise();
        Square[][] frontFaceCopy = frontFace.deepCopy();
        frontFace = rightFace;
        rightFace = backFace;
        backFace = leftFace;
        leftFace.setRow(CubeValues.TOP_ROW.getValue(), frontFaceCopy[CubeValues.TOP_ROW.getValue()]);
        leftFace.setRow(CubeValues.MIDDLE_ROW.getValue(), frontFaceCopy[CubeValues.MIDDLE_ROW.getValue()]);
        leftFace.setRow(CubeValues.BOTTOM_ROW.getValue(), frontFaceCopy[CubeValues.BOTTOM_ROW.getValue()]);
        downFace.rotateCounterclockwise();
    }

    public void shuffle() {

    }
}
