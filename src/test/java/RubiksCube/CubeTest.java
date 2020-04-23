package RubiksCube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class CubeTest {

    Cube cube;

    @BeforeEach
    public void createCube() {
        cube = new Cube();
    }

    @Test
    public void cubeCreatesCorrectlyTest() {

    }

    @Test
    public void rotateVerticalRingDownwardsTest() {
        cube.sliceMiddleLayerCounterclockwise();
        // assertEquals();
    }

    private boolean isCollectionColorCorrect(Square[] collection, Color color) {
        for (int i = 0; i < collection.length; i++) {
            if (!isSquareColorCorrect(collection[i], color)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSquareColorCorrect(Square square, Color color) {
        return square.getColor() == color;
    }
}

        // frontFace = UP_FACE_COLOR
        // leftFace = FRONT_FACE_COLOR
        // topFace = LEFT_FACE_COLOR
        // downFace = RIGHT_FACE_COLOR
        // backFace = DOWN_FACE_COLOR
        // rightFace = BACK_FACE_COLOR