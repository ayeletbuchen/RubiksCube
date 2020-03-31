package RubiksCube;

import javax.swing.*;
import java.util.Random;

public class Cube extends JComponent {

    private Face upFace;
    private Face leftFace;
    private Face rightFace;
    private Face frontFace;
    private Face backFace;
    private Face downFace;
    private Random random;
    private final int NUM_POSSIBLE_ROTATIONS = 18;

    public Cube() {
        upFace = new Face(CubeColors.WHITE.getColor());
        leftFace = new Face(CubeColors.RED.getColor());
        frontFace = new Face(CubeColors.BLUE.getColor());
        rightFace = new Face(CubeColors.ORANGE.getColor());
        backFace = new Face(CubeColors.GREEN.getColor());
        downFace = new Face(CubeColors.YELLOW.getColor());

        random = new Random();

        upFace.setLocation(FrameValues.FRAME_MARGIN + FrameValues.FACE_WIDTH, FrameValues.FRAME_MARGIN);
        leftFace.setLocation(FrameValues.FRAME_MARGIN, upFace.getY() + FrameValues.FACE_WIDTH);
        frontFace.setLocation(FrameValues.FRAME_MARGIN + FrameValues.FACE_WIDTH, leftFace.getY());
        rightFace.setLocation(frontFace.getX() + FrameValues.FACE_WIDTH, leftFace.getY());
        backFace.setLocation(rightFace.getX() + FrameValues.FACE_WIDTH, leftFace.getY());
        downFace.setLocation(frontFace.getX(), frontFace.getY() + FrameValues.FACE_WIDTH);

        add(upFace);
        add(leftFace);
        add(frontFace);
        add(rightFace);
        add(backFace);
        add(downFace);
    }

    public void rotateUpFaceClockwise() {
        upFace.rotateClockwise();
        rotateHorizontalRingCounterclockwise(CubeValues.TOP_ROW.getValue());
    }

    public void rotateUpFaceCounterclockwise() {
        upFace.rotateCounterclockwise();
        rotateHorizontalRingClockwise(CubeValues.TOP_ROW.getValue());
    }

    public void rotateLeftFaceClockwise() {
        leftFace.rotateClockwise();
        rotateVerticalRingDownwards(CubeValues.LEFT_COLUMN.getValue(), true);
    }

    public void rotateLeftFaceCounterclockwise() {
        leftFace.rotateCounterclockwise();
        rotateVerticalRingUpwards(CubeValues.LEFT_COLUMN.getValue(), true);
    }

    public void rotateRightFaceClockwise() {
        rightFace.rotateClockwise();
        rotateVerticalRingUpwards(CubeValues.RIGHT_COLUMN.getValue(), true);
    }

    public void rotateRightFaceCounterclockwise() {
        rightFace.rotateCounterclockwise();
        rotateVerticalRingDownwards(CubeValues.RIGHT_COLUMN.getValue(), true);
    }

    public void rotateFrontFaceClockwise() {
        frontFace.rotateClockwise();
        rotateRingOfRowsAndColumns(true, CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateFrontFaceCounterclockwise() {
        frontFace.rotateCounterclockwise();
        rotateRingOfRowsAndColumns(false, CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateBackFaceClockwise() {
        backFace.rotateClockwise();
        // ring rotation is not clockwise from perspective of back face
        rotateRingOfRowsAndColumns(false, CubeValues.TOP_ROW.getValue());
    }

    public void rotateBackFaceCounterclockwise() {
        backFace.rotateCounterclockwise();
        // ring rotation is clockwise from perspective of back face
        rotateRingOfRowsAndColumns(true, CubeValues.TOP_ROW.getValue());
    }

    public void rotateDownFaceClockwise() {
        downFace.rotateClockwise();
        rotateHorizontalRingClockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void rotateDownFaceCounterclockwise() {
        downFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(CubeValues.BOTTOM_ROW.getValue());
    }

    public void sliceMiddleLayerClockwise() {
        rotateVerticalRingDownwards(CubeValues.MIDDLE_COLUMN.getValue(), false);
    }

    public void sliceMiddleLayerCounterclockwise() {
        rotateVerticalRingUpwards(CubeValues.MIDDLE_COLUMN.getValue(), false);
    }

    public void sliceEquatorialLayerClockwise() {
        rotateHorizontalRingClockwise(CubeValues.MIDDLE_ROW.getValue());
    }

    public void sliceEquatorialLayerCounterclockwise() {
        rotateHorizontalRingCounterclockwise(CubeValues.MIDDLE_ROW.getValue());
    }

    public void sliceStandingLayerClockwise() {
        rotateRingOfRowsAndColumns(true, CubeValues.MIDDLE_ROW.getValue());
    }

    public void sliceStandingLayerCounterclockwise() {
        rotateRingOfRowsAndColumns(false, CubeValues.MIDDLE_ROW.getValue());
    }

    public void turnCubeRight() { // TODO LOOK THIS OVER
        upFace.rotateCounterclockwise();
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
        upFace.rotateClockwise();
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
        for (int rotation = 0; rotation < 5; rotation++) {
            int method = random.nextInt(NUM_POSSIBLE_ROTATIONS);
            switch(method) {
                case 0:
                    rotateUpFaceClockwise();
                    break;
                case 1:
                    rotateUpFaceCounterclockwise();
                    break;
                case 2:
                    rotateLeftFaceClockwise();
                    break;
                case 3:
                    rotateLeftFaceCounterclockwise();
                    break;
                case 4:
                    rotateFrontFaceClockwise();
                    break;
                case 5:
                    rotateFrontFaceCounterclockwise();
                    break;
                case 6:
                    rotateRightFaceClockwise();
                    break;
                case 7:
                    rotateRightFaceCounterclockwise();
                    break;
                case 8:
                    rotateBackFaceClockwise();
                    break;
                case 9:
                    rotateBackFaceCounterclockwise();
                    break;
                case 10:
                    rotateDownFaceClockwise();
                    break;
                case 11:
                    rotateDownFaceCounterclockwise();
                    break;
                case 12:
                    sliceMiddleLayerClockwise();
                    break;
                case 13:
                    sliceMiddleLayerCounterclockwise();
                    break;
                case 14:
                    sliceEquatorialLayerClockwise();
                    break;
                case 15:
                    sliceEquatorialLayerCounterclockwise();
                    break;
                case 16:
                    sliceStandingLayerClockwise();
                    break;
                default:
                    sliceStandingLayerCounterclockwise();
                    break;
            }
        }
    }

    protected Face getUpFace() {
        return upFace;
    }

    protected Face getLeftFace() {
        return leftFace;
    }

    protected Face getFrontFace() {
        return frontFace;
    }

    protected Face getRightFace() {
        return rightFace;
    }

    protected Face getBackFace() {
        return backFace;
    }

    protected Face getDownFace() {
        return downFace;
    }

    private void rotateHorizontalRingCounterclockwise(int row) {
        Square[] frontRow = frontFace.getRowDeepCopy(row);
        Square[] rightRow = rightFace.getRow(row);
        Square[] backRow = backFace.getRow(row);
        Square[] leftRow = leftFace.getRow(row);

        frontFace.setRow(row, rightRow);
        rightFace.setRow(row, backRow);
        backFace.setRow(row, leftRow);
        leftFace.setRow(row, frontRow);
    }

    private void rotateHorizontalRingClockwise(int row) {
        Square[] frontRow = frontFace.getRowDeepCopy(row);
        Square[] rightRow = rightFace.getRow(row);
        Square[] backRow = backFace.getRow(row);
        Square[] leftRow = leftFace.getRow(row);

        frontFace.setRow(row, leftRow);
        leftFace.setRow(row, backRow);
        backFace.setRow(row, rightRow);
        rightFace.setRow(row, frontRow);
    }

    private void rotateVerticalRingUpwards(int column, boolean faceRotation) {
        int backColumnInt = getBackColumnIntForVerticalRing(column, faceRotation);

        Square[] upColumn = upFace.getColumnDeepCopy(column);
        Square[] backColumn = backFace.getColumn(backColumnInt);
        Square[] downColumn = downFace.getColumn(column);
        Square[] frontColumn = frontFace.getColumn(column);

        upFace.setColumn(column, frontColumn);
        frontFace.setColumn(column, downColumn);
        downFace.setColumn(column, backColumn);
        backFace.setColumn(backColumnInt, upColumn);
    }

    private void rotateVerticalRingDownwards(int column, boolean faceRotation) {
        int backColumnInt = getBackColumnIntForVerticalRing(column, faceRotation);

        Square[] upColumn = upFace.getColumnDeepCopy(column);
        Square[] frontColumn = frontFace.getColumn(column);
        Square[] downColumn = downFace.getColumn(column);
        Square[] backColumn = backFace.getColumn(backColumnInt);

        upFace.setColumn(column, backColumn);
        backFace.setColumn(backColumnInt, downColumn);
        downFace.setColumn(column, frontColumn);
        frontFace.setColumn(column, upColumn);
    }

    private int getBackColumnIntForVerticalRing(int column, boolean faceRotation) {
        if (faceRotation) {
            return CubeValues.RIGHT_COLUMN.getValue() - column;
        }
        return column;
    }

    private void rotateRingOfRowsAndColumns(boolean clockwise, int upFaceRow) {
        int up = upFaceRow;
        int down;
        int left;
        int right;

        if (upFaceRow == CubeValues.TOP_ROW.getValue()) {
            right = CubeValues.RIGHT_COLUMN.getValue();
            down = CubeValues.BOTTOM_ROW.getValue();
            left = CubeValues.LEFT_COLUMN.getValue();
        } else if (upFaceRow == CubeValues.MIDDLE_ROW.getValue()) {
            right = CubeValues.MIDDLE_COLUMN.getValue();
            down = CubeValues.MIDDLE_ROW.getValue();
            left = CubeValues.MIDDLE_COLUMN.getValue();
        } else { // Bottom row
            right = CubeValues.LEFT_COLUMN.getValue();
            down = CubeValues.TOP_ROW.getValue();
            left = CubeValues.RIGHT_COLUMN.getValue();
        }

        if (clockwise) {
            rotateRingOfRowsAndColumnsClockwise(up, right, down, left);
        } else {
            rotateRingOfRowsAndColumnsCounterclockwise(up, right, down, left);
        }
    }

    private void rotateRingOfRowsAndColumnsClockwise(int upRow, int rightColumn, int downRow, int leftColumn) {
        Square[] temp = upFace.getRowDeepCopy(upRow);

        upFace.setRow(upRow, leftFace.getColumn(leftColumn));
        leftFace.setColumn(leftColumn, downFace.getRow(downRow));
        downFace.setRow(downRow, rightFace.getColumn(rightColumn));
        rightFace.setColumn(rightColumn, temp);
    }

    private void rotateRingOfRowsAndColumnsCounterclockwise(int upRow, int rightColumn, int downRow, int leftColumn) {
        Square[] temp = upFace.getRowDeepCopy(upRow);

        upFace.setRow(upRow, rightFace.getColumn(rightColumn));
        rightFace.setColumn(rightColumn, downFace.getRow(downRow));
        downFace.setRow(downRow, leftFace.getColumn(leftColumn));
        leftFace.setColumn(leftColumn, temp);
    }
}
