package RubiksCube;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

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
        cube.sliceMiddleColumnsDownwards();
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

        // frontFace = WHITE
        // leftFace = BLUE
        // topFace = RED
        // downFace = ORANGE
        // backFace = YELLOW
        // rightFace = GREEN