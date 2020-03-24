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

    public void rotateBottomFaceClockwise() {
        downFace.rotateClockwise();
        rotateHorizontalRingClockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateBottomFaceCounterclockwise() {
        downFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateMiddleColumnsUpwards() {
        rotateVerticalRingUpwards(CubeValues.MIDDLE_COLUMN.getValue());
    }

    public void rotateMiddleColumnsDownwards() {
        rotateVerticalRingDownwards(CubeValues.MIDDLE_COLUMN.getValue());
    }

    public void rotateMiddleRowsClockwise() {
        rotateHorizontalRingClockwise(CubeValues.MIDDLE_ROW.getValue());
    }

    public void rotateMiddleRowsCounterclockwise() {
        rotateHorizontalRingCounterclockwise(CubeValues.MIDDLE_ROW.getValue());
    }

    public void rotateMiddleRowsAndColumnsRight() {

    }

    public void rotateMiddleRowsAndColumnsLeft() {

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
