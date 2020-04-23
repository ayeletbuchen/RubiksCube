package RubiksCube;

import java.awt.*;

public enum CubeColors {
    FRONT_FACE_COLOR(Color.BLUE),
    BACK_FACE_COLOR(Color.GREEN),
    LEFT_FACE_COLOR(Color.RED),
    UP_FACE_COLOR(Color.WHITE),
    DOWN_FACE_COLOR(Color.YELLOW),
    RIGHT_FACE_COLOR(Color.ORANGE);

    private Color color;

    CubeColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
