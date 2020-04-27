package RubiksCube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CubeTest {

    private Cube cube = new Cube();
    private Square[][] upFaceCopy;
    private Square[][] leftFaceCopy;
    private Square[][] frontFaceCopy;
    private Square[][] rightFaceCopy;
    private Square[][] backFaceCopy;
    private Square[][] downFaceCopy;
    private Square[][] upSquares;
    private Square[][] leftSquares;
    private Square[][] frontSquares;
    private Square[][] rightSquares;
    private Square[][] backSquares;
    private Square[][] downSquares;
    private final int TOP_ROW = CubeValues.TOP_ROW.getValue();
    private final int MIDDLE_ROW = CubeValues.MIDDLE_ROW.getValue();
    private final int BOTTOM_ROW = CubeValues.BOTTOM_ROW.getValue();
    private final int LEFT_COLUMN = CubeValues.LEFT_COLUMN.getValue();
    private final int MIDDLE_COLUMN = CubeValues.MIDDLE_COLUMN.getValue();
    private final int RIGHT_COLUMN = CubeValues.RIGHT_COLUMN.getValue();

    @BeforeEach
    public void shuffleCube() {
        cube.shuffle();
        upFaceCopy = cube.getUpFace().deepCopy();
        leftFaceCopy = cube.getLeftFace().deepCopy();
        frontFaceCopy = cube.getFrontFace().deepCopy();
        rightFaceCopy = cube.getRightFace().deepCopy();
        backFaceCopy = cube.getBackFace().deepCopy();
        downFaceCopy = cube.getDownFace().deepCopy();
        upSquares = cube.getUpFace().squares;
        leftSquares = cube.getLeftFace().squares;
        frontSquares = cube.getFrontFace().squares;
        rightSquares = cube.getRightFace().squares;
        backSquares = cube.getBackFace().squares;
        downSquares = cube.getDownFace().squares;
    }

    @Test
    public void rotateFaceClockwise() {
        cube.getFrontFace().rotateClockwise();
        assertFaceClockwiseRotation(frontFaceCopy, frontSquares);
    }

    @Test
    public void rotateFaceCounterclockwise() {
        cube.getFrontFace().rotateCounterclockwise();
        assertFaceCounterclockwiseRotation(frontFaceCopy, frontSquares);
    }

    @Test
    public void rotateFrontFaceClockwise() {
        cube.rotateFrontFaceClockwise();

        assertEquals(upFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                rightSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());

        assertEquals(rightFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                downSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                downSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                downSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(downFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                leftSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(leftFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
    }

    @Test
    public void rotateFrontFaceCounterclockwise() {
        cube.rotateFrontFaceCounterclockwise();

        assertEquals(upFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(downFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(leftFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                downSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                downSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                downSquares[TOP_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateBackFaceClockwise() {
        cube.rotateBackFaceClockwise();

        assertEquals(rightFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                upSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                upSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                upSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(upFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(leftFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());

        assertEquals(downFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateBackFaceCounterclockwise() {
        cube.rotateBackFaceCounterclockwise();

        assertEquals(upFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                rightSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());

        assertEquals(downFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                leftSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(leftFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                upSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                upSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                upSquares[TOP_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateRightFaceClockwise() {
        cube.rotateRightFaceClockwise();

        assertEquals(upFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                backSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());

        assertEquals(backFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                downSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(downFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(frontFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                upSquares[TOP_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateRightFaceCounterclockwise() {
        cube.rotateRightFaceCounterclockwise();

        assertEquals(upFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(frontFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                downSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(downFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                backSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(backFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                upSquares[TOP_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateLeftFaceClockwise() {
        cube.rotateLeftFaceClockwise();

        assertEquals(upFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());

        assertEquals(frontFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                downSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());

        assertEquals(downFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                backSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                upSquares[TOP_ROW][LEFT_COLUMN].getColor());
    }

    @Test
    public void rotateLeftFaceCounterclockwise() {
        cube.rotateLeftFaceCounterclockwise();

        assertEquals(upFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                backSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                downSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(downFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(downFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(frontFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                upSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
    }

    @Test
    public void rotateUpFaceClockwise() {
        cube.rotateUpFaceClockwise();

        assertEquals(frontFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                leftSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(leftFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                backSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                backSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(backFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                rightSquares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(rightFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[TOP_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateUpFaceCounterclockwise() {
        cube.rotateUpFaceCounterclockwise();

        assertEquals(leftFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(frontFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                rightSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                backSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                backSquares[TOP_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                leftSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[TOP_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateDownFaceClockwise() {
        cube.rotateDownFaceClockwise();

        assertEquals(frontFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(leftFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void rotateDownFaceCounterclockwise() {
        cube.rotateDownFaceCounterclockwise();

        assertEquals(frontFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(leftFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void sliceMiddleLayerClockwise() {
        cube.sliceMiddleLayerClockwise();

        assertEquals(upFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(frontFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(downFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[TOP_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(backFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
    }

    @Test
    public void sliceMiddleLayerCounterclockwise() {
        cube.sliceMiddleLayerCounterclockwise();

        assertEquals(upFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[TOP_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(backFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(downFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(frontFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
    }

    @Test
    public void sliceEquatorialLayerClockwise() {
        cube.sliceEquatorialLayerClockwise();

        assertEquals(frontFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(leftFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void sliceEquatorialLayerCounterclockwise() {
        cube.sliceEquatorialLayerCounterclockwise();

        assertEquals(frontFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(leftFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                backSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(backFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][MIDDLE_ROW].getColor(),
                rightSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(backFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(rightFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void sliceStandingLayerClockwise() {
        cube.sliceStandingLayerClockwise();

        assertEquals(leftFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());

        assertEquals(upFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                rightSquares[TOP_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(rightFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());

        assertEquals(downFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                leftSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
    }

    @Test
    public void sliceStandingLayerCounterclockwise() {
        cube.sliceStandingLayerCounterclockwise();

        assertEquals(upFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                leftSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                leftSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(upFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                leftSquares[TOP_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(leftFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(leftFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(leftFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                downSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());

        assertEquals(downFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                rightSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                rightSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(downFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                rightSquares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(rightFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(rightFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(rightFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                upSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
    }

    @Test
    public void turnCubeRight() {
        cube.turnCubeRight();

        assertFaceEquals(frontFaceCopy, rightSquares);
        assertFaceEquals(rightFaceCopy, backSquares);
        assertFaceEquals(backFaceCopy, leftSquares);
        assertFaceEquals(leftFaceCopy, frontSquares);
        assertFaceCounterclockwiseRotation(upFaceCopy, upSquares);
        assertFaceClockwiseRotation(downFaceCopy, downSquares);
    }

    @Test
    public void turnCubeLeft() {
        cube.turnCubeLeft();

        assertFaceEquals(frontFaceCopy, leftSquares);
        assertFaceEquals(leftFaceCopy, backSquares);
        assertFaceEquals(backFaceCopy, rightSquares);
        assertFaceEquals(rightFaceCopy, frontSquares);
        assertFaceClockwiseRotation(upFaceCopy, upSquares);
        assertFaceCounterclockwiseRotation(downFaceCopy, downSquares);
    }

    private void assertFaceEquals(Square[][] faceCopy, Square[][] squares) {
        for (int row = 0; row < faceCopy.length; row++) {
            for (int col = 0; col < faceCopy[0].length; col++) {
                assertEquals(faceCopy[row][col].getColor(), squares[row][col].getColor());
            }
        }
    }

    private void assertFaceClockwiseRotation(Square[][] faceCopy, Square[][] squares) {
        assertEquals(faceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                squares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(faceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                squares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(faceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                squares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(faceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                squares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(faceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(faceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                squares[BOTTOM_ROW][MIDDLE_ROW].getColor());

        assertEquals(faceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                squares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(faceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                squares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(faceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                squares[BOTTOM_ROW][LEFT_COLUMN].getColor());
    }

    private void assertFaceCounterclockwiseRotation(Square[][] faceCopy, Square[][] squares) {
        assertEquals(faceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                squares[BOTTOM_ROW][LEFT_COLUMN].getColor());
        assertEquals(faceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                squares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(faceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                squares[TOP_ROW][LEFT_COLUMN].getColor());

        assertEquals(faceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                squares[BOTTOM_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(faceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                squares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(faceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                squares[TOP_ROW][MIDDLE_COLUMN].getColor());

        assertEquals(faceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                squares[BOTTOM_ROW][RIGHT_COLUMN].getColor());
        assertEquals(faceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                squares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(faceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                squares[TOP_ROW][RIGHT_COLUMN].getColor());
    }
}