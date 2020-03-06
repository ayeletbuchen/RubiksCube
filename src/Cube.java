import java.util.Arrays;

public class Cube {

    private Face topFace;
    private Face leftFace;
    private Face rightFace;
    private Face frontFace;
    private Face backFace;
    private Face bottomFace;

    public Cube(Face topFace, Face leftFace, Face rightFace, Face frontFace, Face backFace, Face bottomFace) {
        this.topFace = topFace;
        this.leftFace = leftFace;
        this.rightFace = rightFace;
        this.frontFace = frontFace;
        this.backFace = backFace;
        this.bottomFace = bottomFace;
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

    }

    public void rotateLeftFaceCounterclockwise() {

    }

    public void rotateRightFaceClockwise() {

    }

    public void rotateRightFaceCounterclockwise() {

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
        bottomFace.rotateClockwise();
        rotateHorizontalRingClockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateBottomFaceCounterclockwise() {
        bottomFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateMiddleColumnsUpwards() {

    }

    public void rotateMiddleColumnsDownwards() {

    }

    public void rotateMiddleRowsClockwise() {

    }

    public void rotateMiddleRowsCounterclockwise() {

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
}
