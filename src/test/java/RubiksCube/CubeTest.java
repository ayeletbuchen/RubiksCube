package RubiksCube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CubeTest {

    private Cube cube;
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
    public void createCube() {
        cube = new Cube();
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

        assertEquals(frontFaceCopy[TOP_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][RIGHT_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][RIGHT_COLUMN].getColor());
        assertEquals(frontFaceCopy[TOP_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][RIGHT_COLUMN].getColor());

        assertEquals(frontFaceCopy[MIDDLE_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][MIDDLE_COLUMN].getColor());
        assertEquals(frontFaceCopy[MIDDLE_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][MIDDLE_ROW].getColor());

        assertEquals(frontFaceCopy[BOTTOM_ROW][LEFT_COLUMN].getColor(),
                frontSquares[TOP_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][MIDDLE_COLUMN].getColor(),
                frontSquares[MIDDLE_ROW][LEFT_COLUMN].getColor());
        assertEquals(frontFaceCopy[BOTTOM_ROW][RIGHT_COLUMN].getColor(),
                frontSquares[BOTTOM_ROW][LEFT_COLUMN].getColor());
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
}