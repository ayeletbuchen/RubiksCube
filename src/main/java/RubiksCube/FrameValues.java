package RubiksCube;

public interface FrameValues {
    int FRAME_WIDTH = 600;
    int FRAME_HEIGHT = 600;
    int FACE_WIDTH = 100;
    int SQUARE_WIDTH = FACE_WIDTH / 3;
    int PARTIAL_SQUARE_WIDTH = SQUARE_WIDTH / 3;
    int SQUARE_POINTS = 4;
    int DOUBLE_FACE_WIDTH = 2 * FACE_WIDTH;
    int MOST_FACE_WIDTH = (2 * SQUARE_WIDTH) + PARTIAL_SQUARE_WIDTH;

    //<editor-fold desc="Front face coordinates">
    int FRONT_FACE_X_1 = FRAME_WIDTH / 3;
    int FRONT_FACE_X_2 = FRONT_FACE_X_1 + SQUARE_WIDTH;
    int FRONT_FACE_X_3 = FRONT_FACE_X_2 + SQUARE_WIDTH;
    int FRONT_FACE_X_4 = FRONT_FACE_X_3 + SQUARE_WIDTH;
    int[] FRONT_FACE_X = new int[] {FRONT_FACE_X_1, FRONT_FACE_X_2, FRONT_FACE_X_3, FRONT_FACE_X_4};

    int FRONT_FACE_Y_1 = FRAME_HEIGHT / 4;
    int FRONT_FACE_Y_2 = FRONT_FACE_Y_1 + SQUARE_WIDTH;
    int FRONT_FACE_Y_3 = FRONT_FACE_Y_2 + SQUARE_WIDTH;
    int FRONT_FACE_Y_4 = FRONT_FACE_Y_3 + SQUARE_WIDTH;
    int[] FRONT_FACE_Y = new int[] {FRONT_FACE_Y_1, FRONT_FACE_Y_2, FRONT_FACE_Y_3, FRONT_FACE_Y_4};
    //</editor-fold>

    //<editor-fold desc="Up face coordinates">
    //<editor-fold desc="Up face x coordinates">
    int UP_FACE_ROW3_X_1 = FRONT_FACE_X_1 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW3_X_2 = FRONT_FACE_X_2 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW3_X_3 = FRONT_FACE_X_3 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW3_X_4 = FRONT_FACE_X_4 + PARTIAL_SQUARE_WIDTH;
    int[] UP_FACE_ROW3_X = new int[] {UP_FACE_ROW3_X_1, UP_FACE_ROW3_X_2, UP_FACE_ROW3_X_3, UP_FACE_ROW3_X_4};

    int UP_FACE_ROW2_X_1 = UP_FACE_ROW3_X_1 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW2_X_2 = UP_FACE_ROW3_X_2 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW2_X_3 = UP_FACE_ROW3_X_3 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW2_X_4 = UP_FACE_ROW3_X_4 + PARTIAL_SQUARE_WIDTH;
    int[] UP_FACE_ROW2_X = new int[] {UP_FACE_ROW2_X_1, UP_FACE_ROW2_X_2, UP_FACE_ROW2_X_3, UP_FACE_ROW2_X_4};

    int UP_FACE_ROW1_X_1 = UP_FACE_ROW2_X_1 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW1_X_2 = UP_FACE_ROW2_X_2 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW1_X_3 = UP_FACE_ROW2_X_3 + PARTIAL_SQUARE_WIDTH;
    int UP_FACE_ROW1_X_4 = UP_FACE_ROW2_X_4 + PARTIAL_SQUARE_WIDTH;
    int[] UP_FACE_ROW1_X = new int[] {UP_FACE_ROW1_X_1, UP_FACE_ROW1_X_2, UP_FACE_ROW1_X_3, UP_FACE_ROW1_X_4};

    int[][] UP_FACE_X = new int[][] {UP_FACE_ROW1_X, UP_FACE_ROW2_X, UP_FACE_ROW3_X, FRONT_FACE_X};
    //</editor-fold>

    //<editor-fold desc="Up face y coordinates">
    int UP_FACE_Y_3 = FRONT_FACE_Y_1 - PARTIAL_SQUARE_WIDTH;
    int UP_FACE_Y_2 = UP_FACE_Y_3 - PARTIAL_SQUARE_WIDTH;
    int UP_FACE_Y_1 = UP_FACE_Y_2 - PARTIAL_SQUARE_WIDTH;
    int[] UP_FACE_Y = new int[] {UP_FACE_Y_1, UP_FACE_Y_2, UP_FACE_Y_3, FRONT_FACE_Y_1};
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Right face coordinates">
    int[] RIGHT_FACE_X = new int[] {FRONT_FACE_X_4, UP_FACE_ROW3_X_4, UP_FACE_ROW2_X_4, UP_FACE_ROW1_X_4};

    // UP_FACE_Y
    int[] RIGHT_FACE_ROW1_Y = new int[] {UP_FACE_Y[3], UP_FACE_Y[2], UP_FACE_Y[1], UP_FACE_Y[0]};
    int[] RIGHT_FACE_ROW2_Y = new int[] {FRONT_FACE_Y_2, RIGHT_FACE_ROW1_Y[1] + SQUARE_WIDTH,
            RIGHT_FACE_ROW1_Y[2] + SQUARE_WIDTH, RIGHT_FACE_ROW1_Y[3] + SQUARE_WIDTH};
    int[] RIGHT_FACE_ROW3_Y = new int[] {FRONT_FACE_Y_3, RIGHT_FACE_ROW2_Y[1] + SQUARE_WIDTH,
            RIGHT_FACE_ROW2_Y[2] + SQUARE_WIDTH, RIGHT_FACE_ROW2_Y[3] + SQUARE_WIDTH};
    int[] RIGHT_FACE_ROW4_Y = new int[] {FRONT_FACE_Y_4, RIGHT_FACE_ROW3_Y[1] + SQUARE_WIDTH,
            RIGHT_FACE_ROW3_Y[2] + SQUARE_WIDTH, RIGHT_FACE_ROW3_Y[3] + SQUARE_WIDTH};

    int[][] RIGHT_FACE_Y = new int[][] {RIGHT_FACE_ROW1_Y, RIGHT_FACE_ROW2_Y, RIGHT_FACE_ROW3_Y, RIGHT_FACE_ROW4_Y};
    //</editor-fold>

    //<editor-fold desc="Left face coordinates">
    int[][] LEFT_FACE_Y = RIGHT_FACE_Y;
    int[] LEFT_FACE_X = new int[] {RIGHT_FACE_X[0] - DOUBLE_FACE_WIDTH, RIGHT_FACE_X[1] - DOUBLE_FACE_WIDTH,
                    RIGHT_FACE_X[2] - DOUBLE_FACE_WIDTH, RIGHT_FACE_X[3] - DOUBLE_FACE_WIDTH};
    //</editor-fold>

    //<editor-fold desc="Back face coordinates">
    int[] BACK_FACE_X = new int[] {FRONT_FACE_X[0] + DOUBLE_FACE_WIDTH,  FRONT_FACE_X[1] + DOUBLE_FACE_WIDTH,
            FRONT_FACE_X[2] + DOUBLE_FACE_WIDTH, FRONT_FACE_X[3] + DOUBLE_FACE_WIDTH};
    int[] BACK_FACE_Y = new int[] {FRONT_FACE_Y[0] - MOST_FACE_WIDTH, FRONT_FACE_Y[1] - MOST_FACE_WIDTH,
            FRONT_FACE_Y[2] - MOST_FACE_WIDTH, FRONT_FACE_Y[3] - MOST_FACE_WIDTH};
    //</editor-fold>

    //<editor-fold desc="Down face coordinates">
    int[][] DOWN_FACE_X = UP_FACE_X;
    int[] DOWN_FACE_Y = new int[] {UP_FACE_Y[0] + DOUBLE_FACE_WIDTH, UP_FACE_Y[1] + DOUBLE_FACE_WIDTH,
            UP_FACE_Y[2] + DOUBLE_FACE_WIDTH, UP_FACE_Y[3] + DOUBLE_FACE_WIDTH};
    //</editor-fold>
}
