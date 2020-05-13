package RubiksCube;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import io.reactivex.subjects.PublishSubject;

public class Cube extends JComponent implements FrameValues, CubeValues, CubeColors {

    private final Face upFace;
    private final Face leftFace;
    private final Face rightFace;
    private final Face frontFace;
    private final Face backFace;
    private final Face downFace;
    private Random random;
    private final int NUM_POSSIBLE_ROTATIONS = 18;
    PublishSubject<Move> subject;

    public Cube() {
        upFace = new Face(UP_FACE_COLOR);
        leftFace = new Face(LEFT_FACE_COLOR);
        frontFace = new Face(FRONT_FACE_COLOR);
        rightFace = new Face(RIGHT_FACE_COLOR);
        backFace = new Face(BACK_FACE_COLOR);
        downFace = new Face(DOWN_FACE_COLOR);
        random = new Random();
        subject = PublishSubject.create();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawFrontBackFace(graphics, frontFace, FRONT_FACE_X, FRONT_FACE_Y);
        drawUpDownFace(graphics, upFace, UP_FACE_X, UP_FACE_Y);
        drawLeftRightFace(graphics, rightFace, RIGHT_FACE_X, RIGHT_FACE_Y);
        drawUpDownFace(graphics, downFace, DOWN_FACE_X, DOWN_FACE_Y);
        drawFrontBackFace(graphics, backFace, BACK_FACE_X, BACK_FACE_Y);
        drawLeftRightFace(graphics, leftFace, LEFT_FACE_X, LEFT_FACE_Y);
        drawLines(graphics);
    }

    private void drawFrontBackFace(Graphics graphics, Face face, int[] xCoordinates, int[] yCoordinates) {
        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                int colorCol;
                if (face.equals(backFace)) {
                    colorCol = DIMENSION - 1 - col;
                } else {
                    colorCol = col;
                }
                graphics.setColor(face.squares[row][colorCol].getColor());
                graphics.fillPolygon(
                        new int[] {xCoordinates[col], xCoordinates[col + 1],
                                xCoordinates[col + 1], xCoordinates[col]},
                        new int[] {yCoordinates[row], yCoordinates[row],
                                yCoordinates[row + 1], yCoordinates[row + 1]},
                        SQUARE_POINTS);
            }
        }
    }

    private void drawUpDownFace(Graphics graphics, Face face, int[][] xCoordinates, int[] yCoordinates) {
        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                int colorRow;
                if (face.equals(downFace)) {
                    colorRow = DIMENSION - 1 - row;
                } else {
                    colorRow = row;
                }
                graphics.setColor(face.squares[colorRow][col].getColor());
                graphics.fillPolygon(
                        new int[] {xCoordinates[row][col], xCoordinates[row][col + 1],
                                xCoordinates[row + 1][col + 1], xCoordinates[row + 1][col]},
                        new int[] {yCoordinates[row], yCoordinates[row],
                                yCoordinates[row + 1], yCoordinates[row + 1]},
                        SQUARE_POINTS);
            }
        }
    }

    private void drawLeftRightFace(Graphics graphics, Face face, int[] xCoordinates, int[][] yCoordinates) {
        for (int row = 0; row < DIMENSION; row++) {
            for (int col = 0; col < DIMENSION; col++) {
                int colorCol;
                if (face.equals(leftFace)) {
                    colorCol = DIMENSION - 1 - col;
                } else {
                    colorCol = col;
                }
                graphics.setColor(face.squares[row][colorCol].getColor());
                graphics.fillPolygon(new int[] {xCoordinates[col], xCoordinates[col + 1],
                                xCoordinates[col + 1], xCoordinates[col]},
                        new int[] {yCoordinates[row][col], yCoordinates[row][col + 1],
                                yCoordinates[row + 1][col + 1], yCoordinates[row + 1][col]},
                        SQUARE_POINTS);
            }
        }
    }

    private void drawLines(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        drawUpDownFaceLines(graphics, UP_FACE_X, UP_FACE_Y);
        drawFrontBackFaceLines(graphics, FRONT_FACE_X, FRONT_FACE_Y);
        drawLeftRightFaceLines(graphics, RIGHT_FACE_X, RIGHT_FACE_Y);
        drawUpDownFaceLines(graphics, DOWN_FACE_X, DOWN_FACE_Y);
        drawFrontBackFaceLines(graphics, BACK_FACE_X, BACK_FACE_Y);
        drawLeftRightFaceLines(graphics, LEFT_FACE_X, LEFT_FACE_Y);
    }

    private void drawUpDownFaceLines(Graphics graphics, int[][] xCoordinates, int[] yCoordinates) {
        // vertical lines
        graphics.drawLine(xCoordinates[0][0], yCoordinates[0], xCoordinates[3][0], yCoordinates[3]);
        graphics.drawLine(xCoordinates[0][1], yCoordinates[0], xCoordinates[3][1], yCoordinates[3]);
        graphics.drawLine(xCoordinates[0][2], yCoordinates[0], xCoordinates[3][2], yCoordinates[3]);
        graphics.drawLine(xCoordinates[0][3], yCoordinates[0], xCoordinates[3][3], yCoordinates[3]);

        // horizontal lines
        graphics.drawLine(xCoordinates[0][0], yCoordinates[0], xCoordinates[0][3], yCoordinates[0]);
        graphics.drawLine(xCoordinates[1][0], yCoordinates[1], xCoordinates[1][3], yCoordinates[1]);
        graphics.drawLine(xCoordinates[2][0], yCoordinates[2], xCoordinates[2][3], yCoordinates[2]);
        graphics.drawLine(xCoordinates[3][0], yCoordinates[3], xCoordinates[3][3], yCoordinates[3]);
    }

    private void drawFrontBackFaceLines(Graphics graphics, int[] xCoordinates, int[] yCoordinates) {
        // horizontal lines
        graphics.drawLine(xCoordinates[0], yCoordinates[0], xCoordinates[3], yCoordinates[0]);
        graphics.drawLine(xCoordinates[0], yCoordinates[1], xCoordinates[3], yCoordinates[1]);
        graphics.drawLine(xCoordinates[0], yCoordinates[2], xCoordinates[3], yCoordinates[2]);
        graphics.drawLine(xCoordinates[0], yCoordinates[3], xCoordinates[3], yCoordinates[3]);

        // vertical lines
        graphics.drawLine(xCoordinates[0], yCoordinates[0], xCoordinates[0], yCoordinates[3]);
        graphics.drawLine(xCoordinates[1], yCoordinates[0], xCoordinates[1], yCoordinates[3]);
        graphics.drawLine(xCoordinates[2], yCoordinates[0], xCoordinates[2], yCoordinates[3]);
        graphics.drawLine(xCoordinates[3], yCoordinates[0], xCoordinates[3], yCoordinates[3]);
    }

    private void drawLeftRightFaceLines(Graphics graphics, int[] xCoordinates, int[][] yCoordinates) {
        // vertical lines
        graphics.drawLine(xCoordinates[0], yCoordinates[0][0], xCoordinates[0], yCoordinates[3][0]);
        graphics.drawLine(xCoordinates[1], yCoordinates[0][1], xCoordinates[1], yCoordinates[3][1]);
        graphics.drawLine(xCoordinates[2], yCoordinates[0][2], xCoordinates[2], yCoordinates[3][2]);
        graphics.drawLine(xCoordinates[3], yCoordinates[0][3], xCoordinates[3], yCoordinates[3][3]);

        // horizontal lines
        graphics.drawLine(xCoordinates[0], yCoordinates[0][0], xCoordinates[3], yCoordinates[0][3]);
        graphics.drawLine(xCoordinates[0], yCoordinates[1][0], xCoordinates[3], yCoordinates[1][3]);
        graphics.drawLine(xCoordinates[0], yCoordinates[2][0], xCoordinates[3], yCoordinates[2][3]);
        graphics.drawLine(xCoordinates[0], yCoordinates[3][0], xCoordinates[3], yCoordinates[3][3]);
    }

    public void doMove(Move move) {
        switch (move) {
            case U:
                rotateUpFaceClockwise();
                break;
            case L:
                rotateLeftFaceClockwise();
                break;
            case F:
                rotateFrontFaceClockwise();
                break;
            case R:
                rotateRightFaceClockwise();
                break;
            case B:
                rotateBackFaceClockwise();
                break;
            case D:
                rotateDownFaceClockwise();
                break;
            case U_PRIME:
                rotateUpFaceCounterclockwise();
                break;
            case L_PRIME:
                rotateLeftFaceCounterclockwise();
                break;
            case F_PRIME:
                rotateFrontFaceCounterclockwise();
                break;
            case R_PRIME:
                rotateRightFaceCounterclockwise();
                break;
            case B_PRIME:
                rotateBackFaceCounterclockwise();
                break;
            case D_PRIME:
                rotateDownFaceCounterclockwise();
                break;
            case M:
                sliceMiddleLayerClockwise();
                break;
            case E:
                sliceEquatorialLayerClockwise();
                break;
            case S:
                sliceStandingLayerClockwise();
                break;
            case M_PRIME:
                sliceMiddleLayerCounterclockwise();
                break;
            case E_PRIME:
                sliceEquatorialLayerCounterclockwise();
                break;
            case S_PRIME:
                sliceStandingLayerCounterclockwise();
                break;
            case X:
                turnCubeUp();
                break;
            case Y:
                turnCubeLeft();
                break;
            case Z:
                turnCubeClockwiseAlongZAxis();
                break;
            case X_PRIME:
                turnCubeDown();
                break;
            case Y_PRIME:
                turnCubeRight();
                break;
            case Z_PRIME:
                turnCubeCounterclockwiseAlongZAxis();
                break;
        }
    }

    public void rotateUpFaceClockwise() {
        upFace.rotateClockwise();
        rotateHorizontalRingCounterclockwise(TOP_ROW);
        subject.onNext(Move.U);
    }

    public void rotateUpFaceCounterclockwise() {
        upFace.rotateCounterclockwise();
        rotateHorizontalRingClockwise(TOP_ROW);
        subject.onNext(Move.U_PRIME);
    }

    public void rotateLeftFaceClockwise() {
        leftFace.rotateClockwise();
        rotateVerticalRingDownwards(LEFT_COLUMN, true);
        subject.onNext(Move.L);
    }

    public void rotateLeftFaceCounterclockwise() {
        leftFace.rotateCounterclockwise();
        rotateVerticalRingUpwards(LEFT_COLUMN, true);
        subject.onNext(Move.L_PRIME);
    }

    public void rotateRightFaceClockwise() {
        rightFace.rotateClockwise();
        rotateVerticalRingUpwards(RIGHT_COLUMN, true);
        subject.onNext(Move.R);
    }

    public void rotateRightFaceCounterclockwise() {
        rightFace.rotateCounterclockwise();
        rotateVerticalRingDownwards(RIGHT_COLUMN, true);
        subject.onNext(Move.R_PRIME);
    }

    public void rotateFrontFaceClockwise() {
        frontFace.rotateClockwise();
        rotateRingOfRowsAndColumns(true, BOTTOM_ROW);
        subject.onNext(Move.F);
    }

    public void rotateFrontFaceCounterclockwise() {
        frontFace.rotateCounterclockwise();
        rotateRingOfRowsAndColumns(false, BOTTOM_ROW);
        subject.onNext(Move.F_PRIME);
    }

    public void rotateBackFaceClockwise() {
        backFace.rotateClockwise();
        rotateRingOfRowsAndColumns(false, TOP_ROW);
        subject.onNext(Move.B);
    }

    public void rotateBackFaceCounterclockwise() {
        backFace.rotateCounterclockwise();
        rotateRingOfRowsAndColumns(true, TOP_ROW);
        subject.onNext(Move.B_PRIME);
    }

    public void rotateDownFaceClockwise() {
        downFace.rotateClockwise();
        rotateHorizontalRingClockwise(BOTTOM_ROW);
        subject.onNext(Move.D);
    }

    public void rotateDownFaceCounterclockwise() {
        downFace.rotateCounterclockwise();
        rotateHorizontalRingCounterclockwise(BOTTOM_ROW);
        subject.onNext(Move.D_PRIME);
    }

    public void doubleRotateUpFace() {
        rotateUpFaceClockwise();
        rotateUpFaceClockwise();
    }

    public void doubleRotateLeftFace() {
        rotateLeftFaceClockwise();
        rotateLeftFaceClockwise();
    }

    public void doubleRotateFrontFace() {
        rotateFrontFaceClockwise();
        rotateFrontFaceClockwise();
    }

    public void doubleRotateRightFace() {
        rotateRightFaceClockwise();
        rotateRightFaceClockwise();
    }

    public void doubleRotateBackFace() {
        rotateBackFaceClockwise();
        rotateBackFaceClockwise();
    }

    public void doubleRotateDownFace() {
        rotateDownFaceClockwise();
        rotateDownFaceClockwise();
    }

    public void sliceMiddleLayerClockwise() {
        rotateVerticalRingDownwards(MIDDLE_COLUMN, false);
        subject.onNext(Move.M);
    }

    public void sliceMiddleLayerCounterclockwise() {
        rotateVerticalRingUpwards(MIDDLE_COLUMN, false);
        subject.onNext(Move.M_PRIME);
    }

    public void sliceEquatorialLayerClockwise() {
        rotateHorizontalRingClockwise(MIDDLE_ROW);
        subject.onNext(Move.E);
    }

    public void sliceEquatorialLayerCounterclockwise() {
        rotateHorizontalRingCounterclockwise(MIDDLE_ROW);
        subject.onNext(Move.E_PRIME);
    }

    public void sliceStandingLayerClockwise() {
        rotateRingOfRowsAndColumns(true, MIDDLE_ROW);
        subject.onNext(Move.S);
    }

    public void sliceStandingLayerCounterclockwise() {
        rotateRingOfRowsAndColumns(false, MIDDLE_ROW);
        subject.onNext(Move.S_PRIME);
    }

    public void doubleSliceMiddleLayer() {
        sliceMiddleLayerClockwise();
        sliceMiddleLayerClockwise();
    }

    public void doubleSliceEquatorialLayer() {
        sliceEquatorialLayerClockwise();
        sliceEquatorialLayerClockwise();
    }

    public void doubleSliceStandingLayer() {
        sliceStandingLayerClockwise();
        sliceStandingLayerClockwise();
    }

    public void turnCubeRight() {
        upFace.rotateCounterclockwise();
        Square[][] frontFaceCopy = frontFace.deepCopy();

        frontFace.setFace(leftFace);
        leftFace.setFace(backFace);
        backFace.setFace(rightFace);
        rightFace.setFace(frontFaceCopy);

        downFace.rotateClockwise();
        subject.onNext(Move.Y_PRIME);
    }

    public void turnCubeLeft() {
        upFace.rotateClockwise();
        Square[][] frontFaceCopy = frontFace.deepCopy();

        frontFace.setFace(rightFace);
        rightFace.setFace(backFace);
        backFace.setFace(leftFace);
        leftFace.setFace(frontFaceCopy);

        downFace.rotateCounterclockwise();
        subject.onNext(Move.Y);
    }

    public void turnCubeUp() {
        Square[][] frontFaceCopy = frontFace.deepCopy();

        frontFace.setFace(downFace);
        setFaceAfterTurnCubeUp(backFace, downFace);
        setFaceAfterTurnCubeUp(upFace, backFace);
        upFace.setFace(frontFaceCopy);

        rightFace.rotateClockwise();
        leftFace.rotateCounterclockwise();
        subject.onNext(Move.X);
    }

    public void turnCubeDown() {
        Square[][] frontFaceCopy = frontFace.deepCopy();

        frontFace.setFace(upFace);
        setFaceAfterTurnCubeDown(backFace, upFace);
        setFaceAfterTurnCubeDown(downFace, backFace);
        downFace.setFace(frontFaceCopy);

        rightFace.rotateCounterclockwise();
        leftFace.rotateClockwise();
        subject.onNext(Move.X_PRIME);
    }

    public void turnCubeClockwiseAlongZAxis() {
        Square[][] upFaceCopy = upFace.deepCopy();

        setFaceZClockwiseRotation(leftFace, upFace);
        setFaceZClockwiseRotation(downFace, leftFace);
        setFaceZClockwiseRotation(rightFace, downFace);
        setFaceZClockwiseRotation(upFaceCopy, rightFace);

        frontFace.rotateClockwise();
        backFace.rotateCounterclockwise();

        subject.onNext(Move.Z);
    }

    public void turnCubeCounterclockwiseAlongZAxis() {
        Square[][] upFaceCopy = upFace.deepCopy();

        setFaceZCounterclockwiseRotation(rightFace, upFace);
        setFaceZCounterclockwiseRotation(downFace, rightFace);
        setFaceZCounterclockwiseRotation(leftFace, downFace);
        setFaceZCounterclockwiseRotation(upFaceCopy, leftFace);

        frontFace.rotateCounterclockwise();
        backFace.rotateClockwise();
        subject.onNext(Move.Z_PRIME);
    }

    public void doubleHorizontalCubeTurn() {
        turnCubeLeft();
        turnCubeLeft();
    }

    public void doubleVerticalCubeTurn() {
        turnCubeUp();
        turnCubeUp();
    }

    public void doubleCubeTurnAlongZAxis() {
        turnCubeClockwiseAlongZAxis();
        turnCubeClockwiseAlongZAxis();
    }

    public void shuffle() {
        subject.onNext(Move.SHUFFLE);
        for (int rotation = 0; rotation < NUM_SHUFFLE_STEPS; rotation++) {
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

    public void reset() {
        upFace.reset();
        leftFace.reset();
        frontFace.reset();
        rightFace.reset();
        backFace.reset();
        downFace.reset();
        repaint();
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
        setColumnInBackwardsOrder(downFace, column, backColumn);
        setColumnInBackwardsOrder(backFace, backColumnInt, upColumn);
    }

    private void rotateVerticalRingDownwards(int column, boolean faceRotation) {
        int backColumnInt = getBackColumnIntForVerticalRing(column, faceRotation);

        Square[] upColumn = upFace.getColumn(column);
        Square[] frontColumn = frontFace.getColumn(column);
        Square[] downColumn = downFace.getColumnDeepCopy(column);
        Square[] backColumn = backFace.getColumn(backColumnInt);

        downFace.setColumn(column, frontColumn);
        frontFace.setColumn(column, upColumn);
        setColumnInBackwardsOrder(upFace, column, backColumn);
        setColumnInBackwardsOrder(backFace, backColumnInt, downColumn);
    }

    private int getBackColumnIntForVerticalRing(int column, boolean faceRotation) {
        if (faceRotation) {
            return RIGHT_COLUMN - column;
        }
        return column;
    }

    private void rotateRingOfRowsAndColumns(boolean clockwise, int upFaceRow) {
        int up = upFaceRow;
        int down;
        int left;
        int right;

        if (upFaceRow == TOP_ROW) {
            right = RIGHT_COLUMN;
            down = BOTTOM_ROW;
            left = LEFT_COLUMN;
        } else if (upFaceRow == MIDDLE_ROW) {
            right = MIDDLE_COLUMN;
            down = MIDDLE_ROW;
            left = MIDDLE_COLUMN;
        } else { // Bottom row
            right = LEFT_COLUMN;
            down = TOP_ROW;
            left = RIGHT_COLUMN;
        }

        if (clockwise) {
            rotateRingOfRowsAndColumnsClockwise(up, right, down, left);
        } else {
            rotateRingOfRowsAndColumnsCounterclockwise(up, right, down, left);
        }
    }

    private void rotateRingOfRowsAndColumnsClockwise(int upRow, int rightColumn, int downRow, int leftColumn) {
        Square[] temp = upFace.getRowDeepCopy(upRow);

        setRowInBackwardsOrder(upFace, upRow, leftFace.getColumn(leftColumn));
        leftFace.setColumn(leftColumn, downFace.getRow(downRow));
        setRowInBackwardsOrder(downFace, downRow, rightFace.getColumn(rightColumn));
        rightFace.setColumn(rightColumn, temp);
    }

    private void rotateRingOfRowsAndColumnsCounterclockwise(int upRow, int rightColumn, int downRow, int leftColumn) {
        Square[] temp = upFace.getRowDeepCopy(upRow);

        upFace.setRow(upRow, rightFace.getColumn(rightColumn));
        setColumnInBackwardsOrder(rightFace, rightColumn, downFace.getRow(downRow));
        downFace.setRow(downRow, leftFace.getColumn(leftColumn));
        setColumnInBackwardsOrder(leftFace, leftColumn, temp);
    }

    private void setRowInBackwardsOrder(Face face, int row, Square[] newRowOrColArr) {
        face.setRow(row,
                newRowOrColArr[BOTTOM_ROW],
                newRowOrColArr[MIDDLE_ROW],
                newRowOrColArr[TOP_ROW]);
    }

    private void setColumnInBackwardsOrder(Face face, int col, Square[] newRowOrColArr) {
        face.setColumn(col,
                newRowOrColArr[RIGHT_COLUMN],
                newRowOrColArr[MIDDLE_ROW],
                newRowOrColArr[LEFT_COLUMN]);
    }

    private void setFaceZClockwiseRotation(Face fromFace, Face toFace) {
        setFaceZClockwiseRotation(fromFace.squares, toFace);
    }

    private void setFaceZClockwiseRotation(Square[][] fromFace, Face toFace) {
        toFace.setColumn(RIGHT_COLUMN, fromFace[TOP_ROW]);
        toFace.setColumn(MIDDLE_COLUMN, fromFace[MIDDLE_ROW]);
        toFace.setColumn(LEFT_COLUMN, fromFace[BOTTOM_ROW]);
    }

    private void setFaceZCounterclockwiseRotation(Face fromFace, Face toFace) {
        setFaceZCounterclockwiseRotation(fromFace.squares, toFace);
    }

    private void setFaceZCounterclockwiseRotation(Square[][] fromFace, Face toFace) {
        setColumnInBackwardsOrder(toFace, LEFT_COLUMN,
                fromFace[TOP_ROW]);
        setColumnInBackwardsOrder(toFace, MIDDLE_COLUMN,
                fromFace[MIDDLE_ROW]);
        setColumnInBackwardsOrder(toFace, RIGHT_COLUMN,
                fromFace[BOTTOM_ROW]);
    }

    private void setFaceAfterTurnCubeUp(Face fromFace, Face toFace) {
        setFaceAfterTurnCubeUp(fromFace.squares, toFace);
    }

    private void setFaceAfterTurnCubeUp(Square[][] fromFace, Face toFace) {
        setRowInBackwardsOrder(toFace, BOTTOM_ROW, fromFace[TOP_ROW]);
        setRowInBackwardsOrder(toFace, MIDDLE_ROW, fromFace[MIDDLE_ROW]);
        setRowInBackwardsOrder(toFace, TOP_ROW, fromFace[BOTTOM_ROW]);
    }

    private void setFaceAfterTurnCubeDown(Face fromFace, Face toFace) {
        setFaceAfterTurnCubeDown(fromFace.squares, toFace);
    }

    private void setFaceAfterTurnCubeDown(Square[][] fromFace, Face toFace) {
        setRowInBackwardsOrder(toFace, BOTTOM_ROW,
                fromFace[TOP_ROW]);
        setRowInBackwardsOrder(toFace, MIDDLE_ROW,
                fromFace[MIDDLE_ROW]);
        setRowInBackwardsOrder(toFace, TOP_ROW,
                fromFace[BOTTOM_ROW]);
    }
}
