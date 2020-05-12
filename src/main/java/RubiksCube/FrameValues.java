package RubiksCube;

public interface FrameValues {
    int FRAME_WIDTH = 1000;
    int FRAME_HEIGHT = 1350;
    int FACE_WIDTH = 100;
    int SQUARE_MARGIN = 1;
    int FRONT_FACE_X1 = FRAME_WIDTH / 2;
    int FRONT_FACE_X2 = FRONT_FACE_X1 + FACE_WIDTH;
    int FRONT_FACE_Y1 = FRAME_HEIGHT / 3;
    int FRONT_FACE_Y2 = FRONT_FACE_Y1 + FACE_WIDTH;
    int SQUARE_POINTS = 4;
    int SQUARE_WIDTH = FACE_WIDTH / 3;
}
